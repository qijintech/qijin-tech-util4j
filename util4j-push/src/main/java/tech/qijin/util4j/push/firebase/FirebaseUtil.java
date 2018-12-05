package tech.qijin.util4j.push.firebase;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.log.LogFormat;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

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
     * @return
     * @throws FirebaseMessagingException
     */
    public static void send(String token, Notification notification)
            throws FirebaseMessagingException {
        Preconditions.checkState(available, "firebase not available");
        long start = System.currentTimeMillis();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();
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
     * 单播
     *
     * @param token
     * @param notification
     * @param data
     * @return
     * @throws FirebaseMessagingException
     */
    public static void send(String token, Notification notification, Map<String, String> data)
            throws FirebaseMessagingException {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setToken(token)
                .build();

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
     * 广播
     *
     * @param topic
     * @param notification
     * @throws FirebaseMessagingException
     */
    public static void multicast(String topic, Notification notification)
            throws FirebaseMessagingException {

        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        Message message = Message.builder()
                .setNotification(notification)
                .setTopic(topic)
                .build();

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
     * 广播
     *
     * @param topic
     * @param notification
     * @param data
     * @throws FirebaseMessagingException
     */
    public static void multicast(String topic, Notification notification, Map<String, String> data)
            throws FirebaseMessagingException {

        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setTopic(topic)
                .build();

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
     * @param token
     */
    public static void registerTopic(String topic, String token) {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        log.info(LogFormat.builder().message("start to register to topic").build());
        ApiFuture apiFuture = FirebaseMessaging.getInstance().subscribeToTopicAsync(Lists.newArrayList(token), topic);
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<TopicManagementResponse>() {
            @Override
            public void onFailure(Throwable t) {
                log.error(LogFormat.builder()
                        .message("register topic failure")
                        .put("topic", topic)
                        .put("token", token)
                        .put("time cost", System.currentTimeMillis() - start + " ms")
                        .build());
            }

            @Override
            public void onSuccess(TopicManagementResponse result) {
                log.info(LogFormat.builder()
                        .message("register topic success")
                        .put("topic", topic)
                        .put("token", token)
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
    public static void registerTopic(String topic, List<String> tokens) {
        Preconditions.checkState(available, "firebase not available");

        long start = System.currentTimeMillis();

        log.info(LogFormat.builder().message("start to register to topic").build());
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

    public static void main(String[] args) throws IOException, FirebaseMessagingException, InterruptedException {
        String token = "dC2mELMN02U:APA91bH4AScDxUTA54ocrOILawxwKg_6COA_Gpyv7Bp2kvBoqRDTKMXwDGf-y6_oz_VIoastaBIvnb2JVbi_gnAcppTNPZ7FJkWoH5g_Gf8J6tWJjgTf4bsRU0HLeVWQdQ7POM9enR3L";
        FirebaseUtil.send(token, new Notification("mm", "123"));
        log.info("====finished====");

        String topic = "test";
        FirebaseUtil.registerTopic(topic, token);
        FirebaseUtil.multicast(topic, new Notification("multi", "asdf"));
        Thread.sleep(10000);
    }

}
