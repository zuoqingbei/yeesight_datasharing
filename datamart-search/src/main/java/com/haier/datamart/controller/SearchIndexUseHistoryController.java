package com.haier.datamart.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchIndexUseHistory;
import com.haier.datamart.entity.SearchIndexUseHistoryIndex;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchIndexUseHistoryIndexService;
import com.haier.datamart.service.ISearchIndexUseHistoryService;
import com.haier.datamart.service.MailService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * 指标使用记录表 前端控制器
 * </p>
 *
 * @author zuoqb123
 * @since 2018-09-26
 */
@RestController
@RequestMapping("/searchIndexUseHistory")
public class SearchIndexUseHistoryController extends BaseController {
	@Autowired
	private ISearchIndexUseHistoryService iSearchIndexUseHistoryService;
	@Autowired
	private ISearchIndexUseHistoryIndexService iSearchIndexUseHistoryIndexService;
	@Autowired
	private MailService mailService;
	@Autowired
	private ISearchIndexService iSearchindexService;
	@Autowired
	private IAdminDatasourceConfigService configService;

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 新增/修改指标使用记录
	 */
	@RequestMapping(value = "/data/addOrUpdate", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/searchIndexUseHistory/data/addOrUpdate")
	public Object addOrUpdate(HttpServletRequest request,
			@RequestBody SearchIndexUseHistory entity) {
		User user = getLoginUser(request);
		/*
		 * if(StringUtils.isBlank(getLoginUser(request).getId())){ return new
		 * PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!"); }
		 */
		boolean optSuccess = false;
		if (StringUtils.isNotBlank(entity.getId())) {
			// 之前存在 更新
			entity.setUpdateBy(user.getId());
			entity.setUpdateDate(new Date());
			optSuccess = iSearchIndexUseHistoryService.updateById(entity);
			// 删除之前的对照
			EntityWrapper<SearchIndexUseHistoryIndex> wrapper = new EntityWrapper<SearchIndexUseHistoryIndex>();
			wrapper.where("use_histoty_id={0}", entity.getId());
			optSuccess = iSearchIndexUseHistoryIndexService.delete(wrapper);
		} else {
			// 新增
			if (user != null && StringUtils.isNotBlank(user.getId())) {
				entity.setCreateBy(user.getId());
				entity.setCreateDate(new Date());
				entity.setShStatus("0");
			}
			entity.setId(GenerationSequenceUtil.getUUID());
			optSuccess = iSearchIndexUseHistoryService.insert(entity);
		}
		if (optSuccess) {
			for (SearchIndexUseHistoryIndex index : entity.getUseIndexList()) {
				index.setId(GenerationSequenceUtil.getUUID());
				index.setUseHistotyId(entity.getId());
				iSearchIndexUseHistoryIndexService.insert(index);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} else {
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 查询单个指标使用记录
	 */
	@RequestMapping(value = "/data/detail", produces = { "application/json;charset=UTF-8" }, method = { RequestMethod.GET })
	@Log(description = "API接口:/searchIndexUseHistory/data/detail")
	public Object detail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		if (StringUtils.isBlank(getLoginUser(request).getId())) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
					"登录过期,请重新登录!");
		}
		if (StringUtils.isNotBlank(id)) {
			EntityWrapper<SearchIndexUseHistory> hisWrapper = new EntityWrapper<SearchIndexUseHistory>();
			hisWrapper.where("del_flag={0}", 0);
			hisWrapper.where("id={0}", id);
			SearchIndexUseHistory entity = iSearchIndexUseHistoryService
					.selectOne(hisWrapper);
			if (entity != null) {
				EntityWrapper<SearchIndexUseHistoryIndex> wrapper = new EntityWrapper<SearchIndexUseHistoryIndex>();
				wrapper.where("use_histoty_id={0}", entity.getId());
				entity.setUseIndexList(iSearchIndexUseHistoryIndexService
						.selectList(wrapper));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} else {
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY,
					null);
		}
	}

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 查询单个指标使用记录
	 */
	@RequestMapping(value = "/data/delete", produces = { "application/json;charset=UTF-8" }, method = { RequestMethod.POST })
	@Log(description = "API接口:/searchIndexUseHistory/data/delete")
	public Object delete(HttpServletRequest request,
			HttpServletResponse response, String id) {
		if (StringUtils.isBlank(getLoginUser(request).getId())) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
					"登录过期,请重新登录!");
		}
		if (StringUtils.isNotBlank(id)) {
			EntityWrapper<SearchIndexUseHistory> hisWrapper = new EntityWrapper<SearchIndexUseHistory>();
			hisWrapper.where("del_flag={0}", 0);
			hisWrapper.where("id={0}", id);
			if (iSearchIndexUseHistoryService.delete(hisWrapper)) {
				EntityWrapper<SearchIndexUseHistoryIndex> wrapper = new EntityWrapper<SearchIndexUseHistoryIndex>();
				wrapper.where("use_histoty_id={0}", id);
				iSearchIndexUseHistoryIndexService.delete(wrapper);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, "操作成功！");
		} else {
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY,
					"操作失败!");
		}
	}

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 分页查询指标使用记录数据
	 */
	@RequestMapping(value = "/data/list", produces = { "application/json;charset=UTF-8" }, method = { RequestMethod.GET })
	@Log(description = "API接口:/searchIndexUseHistory/data/list")
	public Object list(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			HttpServletRequest request,
			@RequestParam(value = "keyWord", required = false) String keyWord,
			@RequestParam(value = "shStatus", required = false) String shStatus) {
		try {
			if (StringUtils.isBlank(getLoginUser(request).getId())) {
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
						"登录过期,请重新登录!");
			}
			String keyword = request.getParameter("keyword");
			SearchIndexUseHistory s = new SearchIndexUseHistory();
			s.setKeyWord(keyword);
			s.setShStatus(shStatus);
			s.setStartNum((pageNum - 1) * pageSize);
			s.setPageSize(pageSize);
			List<SearchIndexUseHistory> l = iSearchIndexUseHistoryService
					.pageList(s);
			EntityWrapper<SearchIndexUseHistory> wrapper = searchWrapper(
					request, keyword);
			PageHelper.startPage(pageNum, pageSize);
			List<SearchIndexUseHistory> list = iSearchIndexUseHistoryService
					.selectList(wrapper);
			PageInfo<SearchIndexUseHistory> page = new PageInfo<SearchIndexUseHistory>(
					list);
			page.setList(l);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 审核单个指标使用记录
	 */
	@RequestMapping(value = "/data/audit", produces = { "application/json;charset=UTF-8" }, method = { RequestMethod.POST })
	@Log(description = "API接口:/searchIndexUseHistory/data/audit")
	public Object audit(HttpServletRequest request, String id, boolean isPass,
			@RequestParam(value = "shBacks", required = false) String shBacks) {
		if (StringUtils.isBlank(getLoginUser(request).getId())) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
					"登录过期,请重新登录!");
		} else {
			if (StringUtils.isNotBlank(id)) {
				EntityWrapper<SearchIndexUseHistory> hisWrapper = new EntityWrapper<SearchIndexUseHistory>();
				hisWrapper.where("del_flag={0}", 0);
				hisWrapper.where("id={0}", id);
				SearchIndexUseHistory entity = iSearchIndexUseHistoryService
						.selectOne(hisWrapper);
				if (!"1".equals(entity.getShStatus())) {
					entity.setShBacks(shBacks);
					if (isPass) {
						entity.setShStatus("1");
						AdminDatasourceConfig config = configService
								.selectById(entity.getTargetDb());
						if (config == null) {
							return new PublicResult<>(
									PublicResultConstant.FAILED, "目标数据库不存在!");
						}
						List<SearchIndex> indexs = new ArrayList<SearchIndex>();
						if (entity.getUseIndexList() != null
								&& entity.getUseIndexList().size() > 0) {
							List<String> idList = new ArrayList<String>();
							for (SearchIndexUseHistoryIndex i : entity
									.getUseIndexList()) {
								idList.add(i.getIndexId());
							}
							indexs = iSearchindexService.selectBatchIds(idList);
						}
						StringBuffer sb = mailContent(entity, indexs, config);
						if (mailService.sendMail(
								new String[] { entity.getUserEmail() },
								"指标使用申请审核通知", sb.toString(), null)) {
							entity.setMailStatus("1");
						} else {
							entity.setShStatus("2");
							entity.setMailStatus("2");
						}
						entity.setShUser(getLoginUser(request).getId());
						entity.setUpdateDate(new Date());
						if (iSearchIndexUseHistoryService.update(entity,
								hisWrapper)) {
							return new PublicResult<>(
									PublicResultConstant.SUCCESS, "审核成功!");
						} else {
							return new PublicResult<>(
									PublicResultConstant.FAILED, "审核失败,请重新操作!");
						}
					} else {
						entity.setShStatus("2");
						entity.setUpdateDate(new Date());
						if (iSearchIndexUseHistoryService.update(entity,hisWrapper)) {
							return new PublicResult<>(PublicResultConstant.SUCCESS,
									"操作成功!");
						}else{
							
						}return new PublicResult<>(
								PublicResultConstant.FAILED, "审核失败,请重新操作!");
					}

				} else {
					return new PublicResult<>(
							PublicResultConstant.INVALID_PARAM_EMPTY, "编码不能为空!");
				}
			} else {
				return new PublicResult<>(
						PublicResultConstant.INVALID_PARAM_EMPTY, "编码不能为空!");
			}
		}
	}

	/**
	 * @time 2018年9月26日 上午10:00:51
	 * @author zuoqb
	 * @todo 发送邮件
	 */
	@RequestMapping(value = "/data/sendMail", produces = { "application/json;charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@Log(description = "API接口:/searchIndexUseHistory/data/sendMail")
	public Object sendMail(HttpServletRequest request, String id) {
		/*
		 * if(StringUtils.isBlank(getLoginUser(request).getId())){ return new
		 * PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!"); }
		 */
		if (StringUtils.isNotBlank(id)) {
			EntityWrapper<SearchIndexUseHistory> hisWrapper = new EntityWrapper<SearchIndexUseHistory>();
			hisWrapper.where("del_flag={0}", 0);
			hisWrapper.where("id={0}", id);
			SearchIndexUseHistory entity = iSearchIndexUseHistoryService
					.selectOne(hisWrapper);
			if (entity != null) {
				EntityWrapper<SearchIndexUseHistoryIndex> wrapper = new EntityWrapper<SearchIndexUseHistoryIndex>();
				wrapper.where("use_histoty_id={0}", entity.getId());
				entity.setUseIndexList(iSearchIndexUseHistoryIndexService
						.selectList(wrapper));
				List<SearchIndex> indexs = new ArrayList<SearchIndex>();
				if (entity.getUseIndexList() != null
						&& entity.getUseIndexList().size() > 0) {
					List<String> idList = new ArrayList<String>();
					for (SearchIndexUseHistoryIndex i : entity
							.getUseIndexList()) {
						idList.add(i.getIndexId());
					}
					indexs = iSearchindexService.selectBatchIds(idList);
				}
				if ("1".equals(entity.getShStatus())) {
					if ("1".equals(entity.getMailStatus())) {
						return new PublicResult<>(PublicResultConstant.FAILED,
								"已经发送邮件，请勿重复发送!");
					}
					AdminDatasourceConfig config = configService
							.selectById(entity.getTargetDb());
					if (config == null) {
						return new PublicResult<>(PublicResultConstant.FAILED,
								"目标数据库不存在!");
					}
					StringBuffer sb = mailContent(entity, indexs, config);
					if (mailService.sendMail(
							new String[] { entity.getUserEmail() },
							"指标使用申请审核通知", sb.toString(), null)) {
						entity.setMailStatus("1");
						entity.setUpdateBy(getLoginUser(request).getId());
						entity.setUpdateDate(new Date());
						if (iSearchIndexUseHistoryService.update(entity,
								hisWrapper)) {
							return new PublicResult<>(
									PublicResultConstant.SUCCESS, "发送成功!");
						} else {
							return new PublicResult<>(
									PublicResultConstant.FAILED,
									"邮件发送成功，但是状态更新失败!");
						}
					} else {
						return new PublicResult<>(PublicResultConstant.FAILED,
								"邮件发送失败!");
					}

				} else {
					return new PublicResult<>(PublicResultConstant.FAILED,
							"该记录未审核，不能发送邮件!");
				}

			} else {
				return new PublicResult<>(PublicResultConstant.FAILED,
						"该记录不存在！");
			}

		} else {
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY,
					"编码不能为空!");
		}
	}

	protected StringBuffer mailContent(SearchIndexUseHistory entity,
			List<SearchIndex> indexs, AdminDatasourceConfig config) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 生成邮件内容
		StringBuffer sb = new StringBuffer();
		sb.append("<h3>尊敬的用户" + entity.getUserName());
		if (StringUtils.isNotBlank(entity.getUserTel())) {
			sb.append("(电话-" + entity.getUserTel() + ",工号-"
					+ entity.getUserNum() + ")");
		}
		sb.append(":</h3>");
		String content = "";
		for (SearchIndex index : indexs) {
			content += index.getName() + "、";
		}
		if (content.length() > 0) {
			content = content.substring(0, content.length() - 1);
		}
		sb.append("<div style='color:#F00,margin-left:30px;'>");
		sb.append("您于系统时间：" + sdf.format(entity.getCreateDate()));
		sb.append("提出以下指标：" + content + "使用申请，目前已经由管理员");
		if(StringUtils.isNotBlank(entity.getShUserName())&&entity.getShUserName()!=null){
			sb.append(entity.getShUserName());
		}
		sb.append("审核通过!");
		sb.append("<p>");
		sb.append("数据库类型：" + config.getDbType() + "</br>");
		sb.append("数据库地址：" + config.getDbUrl() + "</br>");
		sb.append("数据库账号：" + config.getDbName() + "</br>");
		sb.append("调度地址：" + entity.getManageUrl() + "</br>");
		sb.append("数据库密码请自行联系管理员（电话:18116419906,邮箱:dongshihai@haier.com）索要</br>");
		sb.append("</p>");
		sb.append("</div>");
		return sb;
	}

	/**
	 * @date 2018年9月25日 下午5:36:10
	 * @author zuoqb123
	 * @todo 构建查询条件-以后扩展
	 */
	private EntityWrapper<SearchIndexUseHistory> searchWrapper(
			HttpServletRequest request, String keyword) {
		EntityWrapper<SearchIndexUseHistory> wrapper = new EntityWrapper<SearchIndexUseHistory>();
		wrapper.where("del_flag={0}", 0);
		if (getLoginUser(request) != null
				&& StringUtils.isNotBlank(getLoginUser(request).getId())) {
			if (!"1".equals(getLoginUser(request).getUserType())) {
				wrapper.eq("create_by", getLoginUser(request).getId());
			}
		}
		if (StringUtils.isNotBlank(keyword)) {
			wrapper.andNew();
			wrapper.like("remarks", keyword);

			wrapper.or();
			wrapper.like("user_name", keyword);
			wrapper.or();
			wrapper.like("user_email", keyword);
			wrapper.or();
			wrapper.like("user_num", keyword);
			wrapper.or();
			wrapper.like("leader_name", keyword);
			wrapper.or();
			wrapper.like("leader_email", keyword);
			wrapper.or();
			wrapper.like("leader_tel", keyword);
			wrapper.or();
			wrapper.like("leader_num", keyword);
			wrapper.or();
			wrapper.like("use_reason", keyword);
		}
		wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}

	public static void main(String[] args) {
		SearchIndexUseHistory a = new SearchIndexUseHistory();
		a.setUserName("申请人");
		a.setUserEmail("申请人邮箱");
		a.setUserTel("申请人电话");
		a.setUseReason("使用用途，最多300字");
		a.setUserNum("申请人工号");
		a.setUserDept("申请人部门");
		a.setLeaderName("申请人领导");
		a.setLeaderEmail("申请人领导邮箱");
		a.setLeaderTel("申请人领导电话");
		a.setLeaderNum("申请人领导工号");
		a.setLeaderDept("申请人领导部门");
		List<SearchIndexUseHistoryIndex> indexs = new ArrayList<SearchIndexUseHistoryIndex>();
		SearchIndexUseHistoryIndex i1 = new SearchIndexUseHistoryIndex();
		i1.setIndexId("指标编码1");
		SearchIndexUseHistoryIndex i2 = new SearchIndexUseHistoryIndex();
		i2.setIndexId("指标编码2");
		indexs.add(i1);
		indexs.add(i2);
		a.setUseIndexList(indexs);
		System.out.println(JSON.toJSONString(a));
	}
}
