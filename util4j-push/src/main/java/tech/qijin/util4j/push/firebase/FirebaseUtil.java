package tech.qijin.util4j.push.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.LogFormat;

/**
 * Firebase util
 * <p>所有操作都是异步的</p>
 *
 * @author michealyang
 * @date 2018/12/3
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer https://firebase.google.com/docs/cloud-messaging/admin/send-messages
 **/
@Slf4j(topic = "Firebase")
public class FirebaseUtil {

    private static boolean available = false;
    private static Executor executor = MoreExecutors.directExecutor();

    static {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = FirebaseUtil.class.getClassLoader();
            File file = new File(classLoader.getResource("serviceAccountKey.json").getFile());
            inputStream = new FileInputStream(file);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            FirebaseApp.initializeApp(options);
            available = true;
            log.info(LogFormat.builder().message("firebase initialization succeed!").build());
        } catch (FileNotFoundException e) {
            log.error(LogFormat.builder().message("serviceAccountKey.json not found").build(), e);
        } catch (IOException e) {
            log.error(LogFormat.builder().message("io exception").build(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error(LogFormat.builder().message("close inputstream error").build(), e);
                }
            }
        }
    }

    /**
     * 单播
     *
     * @param token
     * @param notification
     * @param data
     * @return
     * @throws FirebaseMessagingException
     */
    public static void unicast(String token, Notification notification, Map<String, String> data)
            throws FirebaseMessagingException {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        Message.Builder builder = Message.builder()
                .setNotification(notification)
                .setToken(token);
        if (data != null) {
            builder.putAllData(data);
        }

        Message message = builder.build();

        log.info(LogFormat.builder().message("start to send to FCM").build());
        ApiFuture apiFuture = FirebaseMessaging.getInstance().sendAsync(message);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<String>() {
            @Override
            public void onFailure(Throwable t) {
                log.error(LogFormat.builder()
                        .message("send failure")
                        .put("token", token)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }

            @Override
            public void onSuccess(String result) {
                log.info(LogFormat.builder()
                        .message("send success")
                        .put("token", token)
                        .put("result", result)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }
        }, executor);
    }

    /**
     * 组播
     *
     * @param tokens
     * @param notification
     * @param data
     * @throws FirebaseMessagingException
     */
    public static void multicast(List<String> tokens, Notification notification, Map<String, String> data)
            throws FirebaseMessagingException {
        for (String token : tokens) {
            unicast(token, notification, data);
        }
    }

    /**
     * 按topic发送
     *
     * @param topic
     * @param notification
     * @param data
     * @throws FirebaseMessagingException
     */
    public static void topic(String topic, Notification notification, Map<String, String> data)
            throws FirebaseMessagingException {

        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        Message.Builder builder = Message.builder()
                .setNotification(notification)
                .setTopic(topic);
        if (data != null) {
            builder.putAllData(data);
        }

        Message message = builder.build();

        log.info(LogFormat.builder().message("start to multicast to FCM").build());
        ApiFuture apiFuture = FirebaseMessaging.getInstance().sendAsync(message);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<String>() {
            @Override
            public void onFailure(Throwable t) {
                log.error(LogFormat.builder()
                        .message("send failure")
                        .put("topic", topic)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }

            @Override
            public void onSuccess(String result) {
                log.info(LogFormat.builder()
                        .message("send success")
                        .put("topic", topic)
                        .put("result", result)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }
        }, executor);
    }

    /**
     * 注册Topic
     *
     * @param topic
     * @param tokens
     */
    public static void register(String topic, List<String> tokens) {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        log.info(LogFormat.builder().message("start to register to topic").put("topic", topic).build());
        ApiFuture apiFuture = FirebaseMessaging.getInstance().subscribeToTopicAsync(tokens, topic);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<TopicManagementResponse>() {
            @Override
            public void onFailure(Throwable t) {
                log.error(LogFormat.builder()
                        .message("register topic failure")
                        .put("topic", topic)
                        .put("tokens", tokens)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }

            @Override
            public void onSuccess(TopicManagementResponse result) {
                log.info(LogFormat.builder()
                        .message("register topic success")
                        .put("topic", topic)
                        .put("tokens", tokens)
                        .put("result", result)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }
        }, executor);
    }

    /**
     * 取消注册Topic
     *
     * @param topic
     * @param tokens
     */
    public static void unregister(String topic, List<String> tokens) {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        log.info(LogFormat.builder().message("start to unregister from topic").put("topic", topic).build());
        ApiFuture apiFuture = FirebaseMessaging.getInstance().unsubscribeFromTopicAsync(tokens, topic);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<TopicManagementResponse>() {
            @Override
            public void onFailure(Throwable t) {
                log.error(LogFormat.builder()
                        .message("unregister topic failure")
                        .put("topic", topic)
                        .put("tokens", tokens)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }

            @Override
            public void onSuccess(TopicManagementResponse result) {
                log.info(LogFormat.builder()
                        .message("unregister topic success")
                        .put("topic", topic)
                        .put("tokens", tokens)
                        .put("result", result)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }
        }, executor);
    }

    public static void main(String[] args) throws IOException, FirebaseMessagingException, InterruptedException {
        String token = "dC2mELMN02U:APA91bH4AScDxUTA54ocrOILawxwKg_6COA_Gpyv7Bp2kvBoqRDTKMXwDGf-y6_oz_VIoastaBIvnb2JVbi_gnAcppTNPZ7FJkWoH5g_Gf8J6tWJjgTf4bsRU0HLeVWQdQ7POM9enR3L";
        FirebaseUtil.unicast(token, new Notification("mm", "123"), null);
        log.info("====finished====");

        String topic = "test";
        FirebaseUtil.register(topic, Lists.newArrayList(token));
        FirebaseUtil.topic(topic, new Notification("multi", "asdf"), null);
        Thread.sleep(10000);
    }

}
