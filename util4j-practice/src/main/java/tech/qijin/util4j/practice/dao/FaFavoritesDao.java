package tech.qijin.util4j.practice.dao;

import tech.qijin.util4j.practice.mapper.FaFavoritesMapper;
import tech.qijin.util4j.practice.mapper.FaFavoritesSqlProvider;
import tech.qijin.util4j.practice.model.FaFavorites;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.InsertProvider;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import java.util.stream.Collectors;

/**
 * @author: SYSTEM
 **/

public interface FaFavoritesDao extends FaFavoritesMapper {

	@InsertProvider(type = SqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("records") List<FaFavorites> records);

	@InsertProvider(type = SqlProvider.class, method = "batchInsertOrUpdate")
	int batchInsertOrUpdate(@Param("records") List<FaFavorites> records, @Param("columns") List<String> columns);

	class SqlProvider {
		private static final String VALUES = "VALUES";
		FaFavoritesSqlProvider provider = new FaFavoritesSqlProvider();

		public String batchInsert(Map<String, Object> param) {
			List<FaFavorites> records = (List<FaFavorites>) param.get("records");
			return genSqlForBatchInsert(records);
		}

		public String batchInsertOrUpdate(Map<String, Object> param) {
			List<FaFavorites> records = (List<FaFavorites>) param.get("records");
			List<String> columns = (List<String>) param.get("columns");
			return genSqlForBatchInsertOrUpdate(records, columns);
		}

		private String genSqlForBatchInsert(List<FaFavorites> records) {
			List<String> sqls = records.stream()
					.map(record -> provider.insertSelective(record))
					.collect(Collectors.toList());
			String[] arr = sqls.get(0).split(VALUES);
			String head = arr[0];
			String value = arr[1];
			List<String> values = Lists.newArrayList();
			for (int i = 0; i <= sqls.size() - 1; i++) {
				StringBuilder sb = new StringBuilder().append("#{records[").append(i).append("].");
				values.add(value.replace("#{", sb.toString()));
			}
			return new StringBuilder().append(head).append(" ").append(VALUES).append(" ")
					.append(StringUtils.join(values, ",")).toString();
		}

		public String genSqlForBatchInsertOrUpdate(List<FaFavorites> records, List<String> columns) {
			Preconditions.checkArgument(CollectionUtils.isNotEmpty(columns));
			List<String> insertSqls = records.stream()
				.map(record -> provider.insertSelective(record))
				.collect(Collectors.toList());

			List<String> updateSqls = Lists.newArrayList();
			columns.stream().forEach(column ->
				updateSqls.add(new StringBuilder().append(column)
					.append("=VALUES(")
					.append(column)
					.append(")").toString())
			);

			String updateSegment = new StringBuilder()
				.append(" ON DUPLICATE KEY UPDATE ")
				.append(StringUtils.join(updateSqls, ","))
				.toString();
			String[] arr = insertSqls.get(0).split(VALUES);
			String head = arr[0];
			String value = arr[1];
			List<String> values = Lists.newArrayList();
			for (int i = 0; i <= insertSqls.size() - 1; i++) {
				StringBuilder sb = new StringBuilder().append("#{records[").append(i).append("].");
				values.add(value.replace("#{", sb.toString()));
			}
			return new StringBuilder().append(head).append(" ").append(VALUES).append(" ")
					.append(StringUtils.join(values, ","))
					.append(updateSegment)
					.toString();
		}
	}
}

