package com.haier.datamart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasHelp;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.IndexAddHelp;
import com.haier.datamart.entity.NameHelp;
import com.haier.datamart.entity.ScanIndexTableRelative;
import com.haier.datamart.entity.ScanSubjectAreaIndex;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchIndexDimension;
import com.haier.datamart.entity.SearchIndexHistory;
import com.haier.datamart.entity.SearchIndexPlat;
import com.haier.datamart.entity.SearchTags;
import com.haier.datamart.mapper.AdminDataContentDetailMapper;
import com.haier.datamart.mapper.AdminDataContentMapper;
import com.haier.datamart.mapper.AdminDatasourceConfigMapper;
import com.haier.datamart.mapper.DictMapper;
import com.haier.datamart.mapper.ScanIndexTableRelativeMapper;
import com.haier.datamart.mapper.ScanSubjectAreaIndexMapper;
import com.haier.datamart.mapper.SearchIndexDimensionMapper;
import com.haier.datamart.mapper.SearchIndexHistoryMapper;
import com.haier.datamart.mapper.SearchIndexMapper;
import com.haier.datamart.mapper.SearchIndexPlatMapper;
import com.haier.datamart.mapper.SearchTagsMapper;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * 指标表 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@Service
public class SearchIndexServiceImpl extends
		ServiceImpl<SearchIndexMapper, SearchIndex> implements
		ISearchIndexService {
	@Autowired
	private SearchIndexMapper searchindexmapper;
	@Autowired
	private AdminDataContentDetailMapper adminDataContentDetailMapper;
	@Autowired
	private SearchIndexDimensionMapper searchIndexDimensionMapper;
	@Autowired
	private DictMapper dictmapper;
	@Autowired
	private SearchTagsMapper tagsMapper;
	@Autowired
	private ScanSubjectAreaIndexMapper subjectAreaIndexMapper;
	@Autowired
	private AdminDatasourceConfigMapper configMapper;
	@Autowired
	private AdminDataContentMapper contentMapper;
	@Autowired
	private ScanIndexTableRelativeMapper relativeMapper;
	@Autowired
	private ScanIndexTableRelativeMapper tableRelativeMapper;
	@Autowired
	private ScanSubjectAreaIndexMapper areaIndexMapper;
	@Autowired
	private SearchIndexPlatMapper platMapper;
	@Autowired
	private SearchIndexHistoryMapper historyMapper;

	/**
	 * 查询
	 */
	public SearchIndex get(String id) {

		SearchIndex searchindex =searchindexmapper.get(id);
		// 查询指标所属系统
		List<SearchIndexPlat> plats = platMapper.selectbyid(id);
		if (plats.size()>0) {
			searchindex.setPlats(plats);
		}
		List<AdminDataContentDetail> detailList = adminDataContentDetailMapper.getInclude(id);
		searchindex.setAdminDataContentDetailList(detailList);
		Map<String, AdminDataContent> tablse = new HashMap<String, AdminDataContent>();
		Map<String, AdminDatasourceConfig> table = new HashMap<String, AdminDatasourceConfig>();
		for (AdminDataContentDetail d : detailList) {
			AdminDataContent content = new AdminDataContent();
			AdminDatasourceConfig datasourceconfig = new AdminDatasourceConfig();
			content.setTableName(d.getTableName());
			datasourceconfig.setName(d.getName());
			tablse.put(d.getTableName(), content);
			table.put(d.getName(), datasourceconfig);

		}

		searchindex.setAdminDataContentList(mapTransitionList(tablse));
		searchindex.setDatasourceConfigList(mapTransitionList(table));
		List<SearchIndexDimension> dimensionList = searchIndexDimensionMapper
				.getInclude(id);
		searchindex.setSearchIndexDimensionList(dimensionList);
		List<SearchTags> tagsList = tagsMapper.getInclude(new SearchTags(id,
				null));
		searchindex.setTagsList(tagsList);
		List<ScanSubjectAreaIndex> subjectAreaIndexList = subjectAreaIndexMapper
				.getInclude();
		searchindex.setSubjectAreaIndexsList(subjectAreaIndexList);
		return searchindex;
	}

	// map转换成lisy
	public static List mapTransitionList(Map map) {
		List list = new ArrayList();
		Iterator iter = map.entrySet().iterator(); // 获得map的Iterator
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			list.add(entry.getValue());
		}
		return list;
	}

	public List<SearchIndex> findList(Wrapper<SearchIndex> searchindex) {
		return super.selectList(searchindex);
	}

	public Page<SearchIndex> findPage(Page<SearchIndex> page,
			Wrapper<SearchIndex> searchindex) {
		// return super.findPage(page, thinkerApi);
		return super.selectPage(page, searchindex);
	}

	@Override
	public List<SearchIndex> getInclude(String id) {
		// TODO Auto-generated method stub
		return searchindexmapper.getInclude(id);
	}

	@Transactional
	@Override
	public int addExcle(SearchIndex index) {
		// 新增指标历史表
		JSONObject object = JSONObject.fromObject(index);
		SearchIndexHistory history = new SearchIndexHistory();
		history.setId(GenerationSequenceUtil.getUUID());
		history.setIndexId(index.getId());
		history.setJsonData(object.toString());
		history.setCreateDate(new Date());
		history.setCreateBy(index.getCreateBy());
		history.setUpdateDate(new Date());
		history.setUpdateBy(index.getUpdateBy());
		int re = historyMapper.add(history);

		return searchindexmapper.addExcle(index);
	}

	@Override
	public int add(SearchIndexDimension indexDimension) {
		// TODO Auto-generated method stub
		return searchIndexDimensionMapper.add(indexDimension);
	}

	@Override
	public int addConfig(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return configMapper.add(config);
	}

	@Override
	public AdminDatasourceConfig getConfig(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return configMapper.getConfig(config);
	}

	@Override
	public int addContent(AdminDataContent content) {
		// TODO Auto-generated method stub
		return contentMapper.add(content);
	}

	@Override
	public int addDetail(AdminDataContentDetail detail) {
		// TODO Auto-generated method stub
		return adminDataContentDetailMapper.add(detail);
	}

	@Override
	public int addRelative(ScanIndexTableRelative relative) {
		// TODO Auto-generated method stub
		return relativeMapper.add(relative);
	}

	@Override
	public AdminDataContent getContent(AdminDataContent content) {
		// TODO Auto-generated method stub
		return contentMapper.getContent(content);
	}

	@Override
	public AdminDataContentDetail getDetail(AdminDataContentDetail detail) {
		// TODO Auto-generated method stub
		return adminDataContentDetailMapper.getDetail(detail);
	}

	@Override
	public SearchIndex getByName(String name) {
		// TODO Auto-generated method stub
		return searchindexmapper.getByName(name);
	}

	/**
	 * 逻辑删除指标
	 */
	@Override
	public int delete(String id) {
		int re = 0;
		int re1 = 0;
		int re2 = 0;
		int re3 = 0;
		int re4 = 0;
		int re5 = 0;
		SearchIndex index = new SearchIndex();
		index.setDelFlag("1");
		index.setId(id);
		re = searchindexmapper.updateDelete(index);
		// 删指标数据关系
		ScanIndexTableRelative relative = new ScanIndexTableRelative();
		relative.setIndexId(id);
		relative.setDelFlag("1");
		re2 = tableRelativeMapper.updateDelete(relative);
		// 删指标主题域
		ScanSubjectAreaIndex areaIndex = new ScanSubjectAreaIndex();
		areaIndex.setIndexId(id);
		areaIndex.setDelFlag("1");
		re3 = areaIndexMapper.updateDelete(areaIndex);
		// 删维度
		SearchIndexDimension dimension = new SearchIndexDimension();
		dimension.setReportId(id);
		dimension.setDelFlag("1");
		re4 = searchIndexDimensionMapper.updateDelete(dimension);
		// 删应用系统跟目录表
		re5 = platMapper.delete(id);
		return re;

	}

	/**
	 * 指标新增
	 */
	@Transactional
	@Override
	public int addIndex(SearchIndex index) {
		int re = 0;
		// 新增前 根据名字查询是否有重复的 有则删除 本次为修改
		re = searchindexmapper.addExcle(index);
		// 新增指标历史表
		JSONObject object = JSONObject.fromObject(index);
		SearchIndexHistory history = new SearchIndexHistory();
		history.setId(GenerationSequenceUtil.getUUID());
		history.setIndexId(index.getId());
		history.setJsonData(object.toString());
		history.setCreateDate(new Date());
		history.setCreateBy(index.getCreateBy());
		history.setUpdateDate(new Date());
		history.setUpdateBy(index.getUpdateBy());
		re = historyMapper.add(history);
		// 新增主题域
		ScanSubjectAreaIndex areaIndex = new ScanSubjectAreaIndex();
		areaIndex.setId(GenerationSequenceUtil.getUUID());
		areaIndex.setIndexId(index.getId());
		areaIndex.setSubjectAreaId(index.getAreaId());
		areaIndex.setCreateBy(index.getCreateBy());
		areaIndex.setUpdateBy(index.getUpdateBy());
		areaIndex.setCreateDate(new Date());
		areaIndex.setUpdateDate(new Date());
		re = areaIndexMapper.add(areaIndex);
		// 新增维度关系
		String dimString = index.getWeiduId();
		if (dimString != null) {
			String[] dim = dimString.split(",");
			for (String dimm : dim) {

				SearchIndexDimension dimension = new SearchIndexDimension();
				dimension.setId(GenerationSequenceUtil.getUUID());
				dimension.setReportId(index.getId());
				dimension.setDicId(dimm);
				dimension.setCreateBy(index.getCreateBy());
				dimension.setUpdateBy(index.getUpdateBy());
				dimension.setCreateDate(new Date());
				dimension.setUpdateDate(new Date());
				re = searchIndexDimensionMapper.add(dimension);
			}

		}
		// 新增应用系统关系
		int i = 0;
		String system = index.getUseSystem();
		if (StringUtils.isNotBlank(system)) {
			String[] usersystem = system.split(",");
			for (String string : usersystem) {
				SearchIndexPlat plat = new SearchIndexPlat();
				plat.setId(GenerationSequenceUtil.getUUID());
				plat.setDicId(string);
				plat.setIndexId(index.getId());
				// 一个系统对应一个指标目录 获取指标目录
				String cata = index.getCatalogue();
				if (cata != null) {
					String cate[] = cata.split("/");
					plat.setContents(cate[i]);
					i++;
				}
				plat.setCreateDate(new Date());
				plat.setCreateBy(index.getCreateBy());
				plat.setUpdateDate(new Date());
				plat.setUpdateBy(index.getUpdateBy());
				re = platMapper.add(plat);

			}
		}
		List<IndexAddHelp> helps = index.getHelps();
		if (helps != null) {

			for (IndexAddHelp indexAddHelp : helps) {

				List<AdminDataContentDetail> details = indexAddHelp.getDetail();
				for (AdminDataContentDetail admin : details) {
					ScanIndexTableRelative relative = new ScanIndexTableRelative();
					relative.setDataSourceId(indexAddHelp.getDataId());
					relative.setContentId(indexAddHelp.getContentId());
					relative.setContentDeatilId(admin.getId());
					relative.setIndexId(index.getId());
					relative.setUpdateDate(new Date());
					relative.setCreateDate(new Date());
					relative.setCreateBy(index.getCreateBy());
					relative.setUpdateBy(index.getUpdateBy());
					relative.setId(GenerationSequenceUtil.getUUID());
					re = relativeMapper.add(relative);
				}

			}
		}
		return re;
	}

	/**
	 * 指标图谱
	 */
	@Override
	public AltasNameHelp getbyid(String id) {
		AltasNameHelp nameHelps = new AltasNameHelp();
		List<AltasHelp> helps = new ArrayList<AltasHelp>();
		List<NameHelp> name = new ArrayList<NameHelp>();
		List<AdminDataContent> contents = contentMapper.getByIndexid(id);

		for (AdminDataContent adminDataContent : contents) {
			AltasHelp altasHelp = new AltasHelp();
			altasHelp.setSouce(adminDataContent.getId());
			altasHelp.setTarget("I" + id);
			helps.add(altasHelp);
			NameHelp names2 = new NameHelp();
			names2.setId(adminDataContent.getId());
			names2.setName(adminDataContent.getTableName());
			name.add(names2);
			List<AdminDataContentDetail> Details = adminDataContentDetailMapper
					.getByCid(adminDataContent.getId());
			for (AdminDataContentDetail ContentDetail : Details) {
				AltasHelp altas = new AltasHelp();
				altas.setSouce("D" + ContentDetail.getId());
				altas.setTarget(adminDataContent.getId());
				helps.add(altas);
				NameHelp names3 = new NameHelp();
				names3.setId("D" + ContentDetail.getId());
				names3.setName(ContentDetail.getColumnName());
				name.add(names3);
			}
		}

		SearchIndex indexs = searchindexmapper.get(id);
		NameHelp names = new NameHelp();
		names.setId("I" + indexs.getId());
		names.setName(indexs.getName());
		name.add(names);

		nameHelps.setHelpsList(helps);
		nameHelps.setNameList(name);

		return nameHelps;
	}

	@Override
	public List<SearchIndex> getAll(SearchIndex index) {
		List<SearchIndex> indexs = searchindexmapper.getAll(index);
		return indexs;
	}

	/**
	 * 指标详情
	 */
	@Override
	public SearchIndex details(String id) {
		SearchIndex index = searchindexmapper.get(id);
		//查询源数据库名称 
		if (StringUtils.isNotBlank(index.getSourceTableId())) {
			AdminDatasourceConfig config=   configMapper.get(index.getSourceTableId());
			if (config!=null) {
				index.setSourceTableName(config.getDbName());
			}
			
		}
		if (StringUtils.isNotBlank(index.getTargetDbId())) {
			AdminDatasourceConfig config=  configMapper.get(index.getTargetDbId());
	        if (config!=null) {
	        	index.setTargetDbName(config.getDbName());
			} 
			
		}
		       
		 List<SearchIndexPlat> plats = platMapper.selectbyid(id);
		 if (plats.size()>0) {
			 index.setPlats(plats);	
		}
		List<ScanSubjectAreaIndex> areaIndexs = areaIndexMapper.getIndex(id);
		index.setSubjectAreaIndexsList(areaIndexs);
		List<SearchIndexDimension> dimensions = searchIndexDimensionMapper
				.getIndex(id);
		index.setSearchIndexDimensionList(dimensions);
		List<IndexAddHelp> helps = new ArrayList<IndexAddHelp>();
		List<ScanIndexTableRelative> relative = relativeMapper.getIndex(id);
		for (ScanIndexTableRelative scanIndexTableRelative : relative) {
			IndexAddHelp addHelp = new IndexAddHelp();
			ScanIndexTableRelative relative2 = new ScanIndexTableRelative();
			relative2.setContentId(scanIndexTableRelative.getContentId());
			relative2.setDataSourceId(scanIndexTableRelative.getDataSourceId());
			relative2.setIndexId(id);
			List<AdminDataContentDetail> details = relativeMapper
					.getColumn(relative2);
			addHelp.setContentId(scanIndexTableRelative.getContentId());
			AdminDataContent content = contentMapper.get(scanIndexTableRelative.getContentId());
			if (content!=null) {
				addHelp.setTabelName(content.getTableName());	
			}
			addHelp.setDataId(scanIndexTableRelative.getDataSourceId());
			AdminDatasourceConfig config = configMapper
					.get(scanIndexTableRelative.getDataSourceId());
			if(config!=null)
			addHelp.setDbName(config.getName());
			addHelp.setDetail(details);
			helps.add(addHelp);
		}
		index.setHelps(helps);
		return index;
	}

	/**
	 * 指标修改
	 */
	@Override
	public int update(SearchIndex index) {
		int re = 0;
		// 修改指标实体
		re = searchindexmapper.update(index);
		// 查历史记录比较两个指标是否真的修改，修改则新增记录
		JSONObject object = JSONObject.fromObject(index);
		List<SearchIndexHistory> indexHistories = historyMapper.getbytime(index
				.getId());
		if (indexHistories!=null&&indexHistories.size()>0) {
			System.out.println(indexHistories.get(0));
			JSONObject jsonindex = JSONObject.fromObject(indexHistories.get(0)
					.getJsonData());
			if (!object.toString().equals(jsonindex.toString())) {
				SearchIndexHistory history = new SearchIndexHistory();
				history.setId(GenerationSequenceUtil.getUUID());
				history.setIndexId(index.getId());
				history.setJsonData(object.toString());
				history.setCreateDate(new Date());
				history.setCreateBy(index.getCreateBy());
				history.setUpdateDate(new Date());
				history.setUpdateBy(index.getUpdateBy());
				re = historyMapper.add(history);
			}
			}else {
				SearchIndexHistory history = new SearchIndexHistory();
				history.setId(GenerationSequenceUtil.getUUID());
				history.setIndexId(index.getId());
				history.setJsonData(object.toString());
				history.setCreateDate(new Date());
				history.setCreateBy(index.getCreateBy());
				history.setUpdateDate(new Date());
				history.setUpdateBy(index.getUpdateBy());
				re = historyMapper.add(history);
			}
		// 修改维度前，删除原有的维度
		// 删除原有维度关系
		SearchIndexDimension indexDimension = new SearchIndexDimension();
		indexDimension.setReportId(index.getId());
		re = searchIndexDimensionMapper.updateDelete(indexDimension);
		String weidu = index.getWeiduId();
		if (weidu != null) {
			String dim[] = weidu.split(",");
			for (String dimm : dim) {
				SearchIndexDimension dimension = new SearchIndexDimension();
				dimension.setId(GenerationSequenceUtil.getUUID());
				dimension.setReportId(index.getId());
				dimension.setDicId(dimm);
				dimension.setCreateBy(index.getCreateBy());
				dimension.setUpdateBy(index.getUpdateBy());
				dimension.setCreateDate(new Date());
				dimension.setUpdateDate(new Date());

				// 新增
				re = searchIndexDimensionMapper.add(dimension);

			}
		}
		// 修改所属系统及目录前先删除原有
		re = platMapper.delete(index.getId());
		// 删除成功后则新增
		int i = 0;
		String system = index.getUseSystem();
		if (StringUtils.isNotBlank(system)) {
			String[] usersystem = system.split(",");
			for (String string : usersystem) {
				SearchIndexPlat plat = new SearchIndexPlat();
				plat.setId(GenerationSequenceUtil.getUUID());
				plat.setDicId(string);
				plat.setIndexId(index.getId());
				// 一个系统对应一个指标目录 获取指标目录
				String cata = index.getCatalogue();
				if (cata != null) {
					String cate[] = cata.split("/");
					if(cate.length>i)
					plat.setContents(cate[i]);
					i++;
				}
				plat.setCreateDate(new Date());
				plat.setCreateBy(index.getCreateBy());
				plat.setUpdateDate(new Date());
				plat.setUpdateBy(index.getUpdateBy());
				re = platMapper.add(plat);

			}
		}

		// 修改域,但一个
		ScanSubjectAreaIndex areaIndex = new ScanSubjectAreaIndex();
		areaIndex.setIndexId(index.getId());
		areaIndex.setSubjectAreaId(index.getAreaId());
		areaIndex.setUpdateDate(new Date());
		areaIndex.setUpdateBy(index.getUpdateBy());
		re = areaIndexMapper.updatebyid(areaIndex);

		// 修改数据库
		List<IndexAddHelp> helps = index.getHelps();
		if (helps != null) {
			// 删除原有数据跟指标
			ScanIndexTableRelative tableRelative = new ScanIndexTableRelative();
			tableRelative.setIndexId(index.getId());
			tableRelative.setDelFlag("1");
			//re = tableRelativeMapper.updateDelete(tableRelative);
			EntityWrapper<ScanIndexTableRelative> wrapper = new EntityWrapper<ScanIndexTableRelative>();
			wrapper.where("1=1");
			wrapper.and("index_id", index.getId());
			tableRelativeMapper.delete(wrapper);
			for (IndexAddHelp indexAddHelp : helps) {

				List<AdminDataContentDetail> details = indexAddHelp.getDetail();
				if(details!=null&&details.size()>0){
					for (AdminDataContentDetail admin : details) {
						ScanIndexTableRelative relative = new ScanIndexTableRelative();
						relative.setDataSourceId(indexAddHelp.getDataId());
						relative.setContentId(indexAddHelp.getContentId());
						relative.setContentDeatilId(admin.getId());
						relative.setIndexId(index.getId());
						relative.setUpdateDate(new Date());
						relative.setCreateDate(new Date());
						relative.setCreateBy(index.getCreateBy());
						relative.setUpdateBy(index.getUpdateBy());
						relative.setId(GenerationSequenceUtil.getUUID());
						re = relativeMapper.add(relative);
					}
				}
			}
		}
		return re;
	}

	@Override
	public SearchIndex getName(String name) {

		return searchindexmapper.getName(name);
	}
	
	/**
	 * 根据指标编码获取指标实体
	 */
	@Override
	public List<SearchIndex> getEntriesByIndexCodes(List<String> list) {
		return  searchindexmapper.getEntriesByIndexCodes(list);
	}
    /**
     * 根据系统id 查询该系统下的指标
     */
	@Override
	public List<SearchIndex> getbySYS(String name) {
		
		return searchindexmapper.getbySYS(name);
	}
    /**
     * 
     */
	@Override
	public List<SearchIndex> getbyReport(String reportId) {
		
		return searchindexmapper.getbyReport(reportId);
	}

	@Override
	public List<SearchIndex> getbyCode(String code) {
		
		return searchindexmapper.getbyCode(code);
	}
	@Override
	public List<SearchIndex> getUserSeeIndex(String userId) {
		// TODO Auto-generated method stub
		return searchindexmapper.getUserSeeIndex(userId);

	}

	@Override
	public List<SearchIndex> getbyReportandName(String reportId,
			String indexname) {
		// TODO Auto-generated method stub
		return searchindexmapper.getbyReportandName(reportId, indexname);
	}

	@Override
	public List<SearchIndex> getplat() {
		// TODO Auto-generated method stub
		return searchindexmapper.getplat();
	}

	@Override
	public List<SearchIndex> getIndexBySubject(String subjectId) {
		// TODO Auto-generated method stub
		return searchindexmapper.getIndexBySubject(subjectId);
	}

	/*
	 * public void save(ThinkerApi thinkerApi) { apiMapper.insert(thinkerApi);
	 * for (ThinkerApiErrorcode thinkerApiErrorcode : thinkerApi
	 * .getThinkerApiErrorcodeList()) { if (thinkerApiErrorcode.getId() == null)
	 * { continue; } if
	 * (ThinkerApiErrorcode.DEL_FLAG_NORMAL.equals(thinkerApiErrorcode
	 * .getDelFlag())) { if (StringUtils.isBlank(thinkerApiErrorcode.getId())) {
	 * thinkerApiErrorcode.setApiId(thinkerApi.getId());
	 * thinkerapierrorcodemapper.insert(thinkerApiErrorcode); } else {
	 * thinkerapierrorcodemapper.update(thinkerApiErrorcode); } } else {
	 * thinkerapierrorcodemapper.delete(thinkerApiErrorcode); } } for
	 * (ThinkerApiParam thinkerApiParam : thinkerApi .getThinkerApiParamList())
	 * { if (thinkerApiParam.getId() == null) { continue; } if
	 * (ThinkerApiParam.DEL_FLAG_NORMAL.equals(thinkerApiParam .getDelFlag())) {
	 * if (StringUtils.isBlank(thinkerApiParam.getId())) {
	 * thinkerApiParam.setApiId(thinkerApi.getId());
	 * thinkerapiparammapper.insert(thinkerApiParam); } else {
	 * thinkerapiparammapper.update(thinkerApiParam); } } else {
	 * thinkerapiparammapper.delete(thinkerApiParam); } } for (ThinkerApiSuccess
	 * thinkerApiSuccess : thinkerApi .getThinkerApiSuccessList()) { if
	 * (thinkerApiSuccess.getId() == null) { continue; } if
	 * (ThinkerApiSuccess.DEL_FLAG_NORMAL.equals(thinkerApiSuccess
	 * .getDelFlag())) { if (StringUtils.isBlank(thinkerApiSuccess.getId())) {
	 * thinkerApiSuccess.setApiId(thinkerApi.getId());
	 * thinkerapisuccessmapper.insert(thinkerApiSuccess); } else {
	 * thinkerapisuccessmapper.update(thinkerApiSuccess); } } else {
	 * thinkerapisuccessmapper.delete(thinkerApiSuccess); } } }
	 *//**
	 * 删除
	 */
	/*
	 * public void delete(ThinkerApi thinkerApi) { apiMapper.delete(thinkerApi);
	 * ThinkerApiErrorcode apiErrorcode = new ThinkerApiErrorcode();
	 * ThinkerApiParam apiParam = new ThinkerApiParam(); ThinkerApiSuccess
	 * apiSuccess = new ThinkerApiSuccess();
	 * apiErrorcode.setApiId(thinkerApi.getId());
	 * apiParam.setApiId(thinkerApi.getId());
	 * apiSuccess.setApiId(thinkerApi.getId());
	 * thinkerapierrorcodemapper.delete(apiErrorcode);
	 * thinkerapiparammapper.delete(apiParam);
	 * thinkerapisuccessmapper.delete(apiSuccess); }
	 */
}
