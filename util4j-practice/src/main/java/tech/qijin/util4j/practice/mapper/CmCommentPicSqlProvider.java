package tech.qijin.util4j.practice.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import tech.qijin.util4j.practice.model.CmCommentPic;
import tech.qijin.util4j.practice.model.CmCommentPicExample.Criteria;
import tech.qijin.util4j.practice.model.CmCommentPicExample.Criterion;
import tech.qijin.util4j.practice.model.CmCommentPicExample;

public class CmCommentPicSqlProvider {

    public String countByExample(CmCommentPicExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("cm_comment_pic");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CmCommentPicExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("cm_comment_pic");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(CmCommentPic record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("cm_comment_pic");
        
        if (record.getCommentId() != null) {
            sql.VALUES("comment_id", "#{commentId,jdbcType=BIGINT}");
        }
        
        if (record.getPic() != null) {
            sql.VALUES("pic", "#{pic,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.VALUES("channel", "#{channel,jdbcType=INTEGER}");
        }
        
        if (record.getEnv() != null) {
            sql.VALUES("env", "#{env,jdbcType=INTEGER}");
        }
        
        if (record.getValid() != null) {
            sql.VALUES("valid", "#{valid,jdbcType=INTEGER}");
        }
        
        if (record.getCtime() != null) {
            sql.VALUES("ctime", "#{ctime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUtime() != null) {
            sql.VALUES("utime", "#{utime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(CmCommentPicExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("comment_id");
        sql.SELECT("pic");
        sql.SELECT("channel");
        sql.SELECT("env");
        sql.SELECT("valid");
        sql.SELECT("ctime");
        sql.SELECT("utime");
        sql.FROM("cm_comment_pic");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        CmCommentPic record = (CmCommentPic) parameter.get("record");
        CmCommentPicExample example = (CmCommentPicExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("cm_comment_pic");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getCommentId() != null) {
            sql.SET("comment_id = #{record.commentId,jdbcType=BIGINT}");
        }
        
        if (record.getPic() != null) {
            sql.SET("pic = #{record.pic,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.SET("channel = #{record.channel,jdbcType=INTEGER}");
        }
        
        if (record.getEnv() != null) {
            sql.SET("env = #{record.env,jdbcType=INTEGER}");
        }
        
        if (record.getValid() != null) {
            sql.SET("valid = #{record.valid,jdbcType=INTEGER}");
        }
        
        if (record.getCtime() != null) {
            sql.SET("ctime = #{record.ctime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUtime() != null) {
            sql.SET("utime = #{record.utime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("cm_comment_pic");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("comment_id = #{record.commentId,jdbcType=BIGINT}");
        sql.SET("pic = #{record.pic,jdbcType=VARCHAR}");
        sql.SET("channel = #{record.channel,jdbcType=INTEGER}");
        sql.SET("env = #{record.env,jdbcType=INTEGER}");
        sql.SET("valid = #{record.valid,jdbcType=INTEGER}");
        sql.SET("ctime = #{record.ctime,jdbcType=TIMESTAMP}");
        sql.SET("utime = #{record.utime,jdbcType=TIMESTAMP}");
        
        CmCommentPicExample example = (CmCommentPicExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(CmCommentPic record) {
        SQL sql = new SQL();
        sql.UPDATE("cm_comment_pic");
        
        if (record.getCommentId() != null) {
            sql.SET("comment_id = #{commentId,jdbcType=BIGINT}");
        }
        
        if (record.getPic() != null) {
            sql.SET("pic = #{pic,jdbcType=VARCHAR}");
        }
        
        if (record.getChannel() != null) {
            sql.SET("channel = #{channel,jdbcType=INTEGER}");
        }
        
        if (record.getEnv() != null) {
            sql.SET("env = #{env,jdbcType=INTEGER}");
        }
        
        if (record.getValid() != null) {
            sql.SET("valid = #{valid,jdbcType=INTEGER}");
        }
        
        if (record.getCtime() != null) {
            sql.SET("ctime = #{ctime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUtime() != null) {
            sql.SET("utime = #{utime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, CmCommentPicExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}