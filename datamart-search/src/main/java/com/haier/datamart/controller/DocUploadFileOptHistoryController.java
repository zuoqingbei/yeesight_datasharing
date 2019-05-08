package com.haier.datamart.controller;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.enterise.web.htmlgen.doc.Word2PdfUtil;
import com.enterise.web.htmlgen.pdf.PdfToImage;
import com.enterise.web.htmlgen.ppt.JacobWord2PDF;
import com.enterise.web.htmlgen.ppt.Ppt2PdfUtils;
import com.enterise.web.htmlgen.xls.ExcelToHtml;
import com.github.pagehelper.PageHelper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.entity.DocUploadFileOptHistory;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IDocUploadFileOptHistoryService;
import com.haier.datamart.service.IDocUploadFileService;
import com.haier.datamart.utils.FileUtil;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.utils.JWT.utils.GsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author ZhangJiang
 * @date 2018-12-17 文件、文件夹操作记录表路由
 */
@RestController
@RequestMapping("/api/docUploadFileOptHistory")
@Api(value = "文件、文件夹操作记录表", description = "文件、文件夹操作记录表 @author ZhangJiang123")
public class DocUploadFileOptHistoryController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(DocUploadFileOptHistoryController.class);

	@Autowired
	public IDocUploadFileOptHistoryService iDocUploadFileOptHistoryService;
	@Autowired
	public IDocUploadFileService iDocUploadFileService;

	/**
	 * 最近使用添加
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "最近使用添加", notes = "最近使用添加", httpMethod = "POST")
	@RequestMapping(value = "/recentlyUsedAdd", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public PublicResult<DocUploadFileOptHistory> recentlyUsedAdd(HttpServletRequest request,
			DocUploadFileOptHistory entity) {
		PublicResultConstant publicResultConstant = PublicResultConstant.FAILED;
		try {
//  			String optType = entity.getOptType();
//  			操作类型 0-预览 1-下载  2-修改 3-上传 4-删除 5-恢复
//  			if(null != optType && ("0".equals(optType)||"3".equals(optType))) {
			String loginName = getLoginUser(request).getLoginName();
			entity.setCreateBy(loginName);
			entity.setUpdateBy(loginName);
			Integer i = iDocUploadFileOptHistoryService.recentlyUsedAdd(entity);
			String msg = "";
			if (i == 1) {
				publicResultConstant = PublicResultConstant.SUCCESS;
				msg = "成功";
			} else {
				publicResultConstant = PublicResultConstant.ERROR;
				msg = "失败";
			}
			return new PublicResult<>(publicResultConstant, "添加成功" + msg, null);
//  			}else {
//  				return new PublicResult<>(PublicResultConstant.ERROR,"操作类型无效",null);
//  			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(publicResultConstant, e.getMessage(), null);
		}
	}

	/**
	 * 最近使用查询
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "最近使用查询", notes = "最近使用查询", httpMethod = "POST")
	@RequestMapping(value = "/recentlyUsedQuery", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public Object recentlyUsedQuery(DocUploadFileOptHistory entity,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		return this.executeQuery(entity, pageNum, pageSize, request, "opt_type = {0} or opt_type = {1}", "0", "3");
	}

	/**
	 * 回收站查询
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "回收站查询", notes = "回收站查询", httpMethod = "POST")
	@RequestMapping(value = "/recycleBinQuery", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public Object recycleBinQuery(DocUploadFileOptHistory entity,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		return this.executeQuery(entity, pageNum, pageSize, request, "opt_type = {0}", "4");
	}

	/**
	 * 更新文件记录查询
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "更新文件记录查询", notes = "更新文件记录查询", httpMethod = "POST")
	@RequestMapping(value = "/updateFileRecordQuery", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public Object updateFileRecordQuery(DocUploadFileOptHistory entity,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		return this.executeQuery(entity, pageNum, pageSize, request, "opt_type = {0}", "2");
	}

	/**
	 * @date 2018-12-18
	 * @author ZhangJiang
	 * @todo 根据id更新文件
	 */
	@ApiOperation(value = "根据id更新文件", notes = "根据id更新文件", httpMethod = "POST")
	@RequestMapping(value = "/updateFileById", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public Object updateFileById(HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "userCode", required = false) String userCode) {
		try {
			DocUploadFile entity = new DocUploadFile();
			// 获取文件名
			String fileName = file.getOriginalFilename();
			fileName = new String(fileName.getBytes("UTF-8"), FileUtil.FILE_ENCODE);

			String fileType = FileUtil.getFileExt(fileName);
			int index = fileName.lastIndexOf('.');
			fileName = fileName.substring(0, index);
			// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size = file.getSize();
			if (StringUtils.isEmpty(fileName) || size == 0) {
				return new PublicResult<>(PublicResultConstant.ERROR, "文件不能为空！", null);
			}
			if ("doc".equals(fileType) || "docx".equals(fileType) || "xls".equals(fileType) || "xlsx".equals(fileType)
					|| "pdf".equals(fileType) || "png".equals(fileType) || "jpg".equals(fileType)
					|| "ppt".equals(fileType) || "pptx".equals(fileType)) {
				InputStream is = null;
				// 根据新建的文件实例化输入流
				is = file.getInputStream();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String dateStr = sdf.format(new Date());
				String uploadName = fileName + "(" + dateStr + ")." + fileType;
				String filePath = FileUtil.savePic(is, uploadName);
				String htmlName = "";// 转化后html页面名称
				try {
					switch (fileType) {
					case "xls":
						htmlName = ExcelToHtml.excel2Html(filePath + File.separator, uploadName, fileName);
						break;
					case "xlsx":
						htmlName = ExcelToHtml.excel2Html(filePath + File.separator, uploadName, fileName);
						break;
					case "doc":
						String outNameDoc = fileName + "(" + dateStr + ").pdf";
						boolean successDoc = Word2PdfUtil.doc2pdf(filePath + File.separator + uploadName,
								filePath + File.separator + outNameDoc);
						if (successDoc) {
							htmlName = PdfToImage.pdfToImage(filePath + File.separator, outNameDoc, fileName);
						}
						break;
					case "docx":
						String outName = fileName + "(" + dateStr + ").pdf";
						boolean success = Word2PdfUtil.doc2pdf(filePath + File.separator + uploadName,
								filePath + File.separator + outName);
						if (success) {
							htmlName = PdfToImage.pdfToImage(filePath + File.separator, outName, fileName);
						}
						break;
					case "pdf":
						htmlName = PdfToImage.pdfToImage(filePath + File.separator, uploadName, fileName);
						break;
					case "ppt":
						String outNameDocPPt = fileName + "(" + dateStr + ").pdf";
						boolean successPpt = Ppt2PdfUtils.ppt2Pdf(filePath + File.separator + uploadName,
								filePath + File.separator + outNameDocPPt, fileType);
						if (successPpt) {
							htmlName = PdfToImage.pdfToImage(filePath + File.separator, outNameDocPPt, fileName);
						}
						break;
					case "pptx":
						String outNamePptx = fileName + "(" + dateStr + ").pdf";
						Map<String, Object> successPptx = JacobWord2PDF.convert2PDFByJacob(
								filePath + File.separator + uploadName, filePath + File.separator + outNamePptx);
						if ("true".equals(successPptx.get("success").toString())) {
							htmlName = PdfToImage.pdfToImage(filePath + File.separator, outNamePptx, fileName);
						}
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String userId = getLoginUser(request).getId();
				entity.setId(id);
				entity.setUpdateDate(new Date());
				if (StringUtils.isBlank(userCode)) {
					entity.setUpdateBy(userId);
				} else {
					entity.setUpdateBy(userCode);
				}
				entity.setUpdateBy(getLoginUser(request).getId());
				entity.setOriginalname(fileName);
				entity.setUploadName(uploadName);
				entity.setExt(fileType);
				entity.setPath(filePath);
				entity.setSize(Integer.valueOf(size + ""));
				entity.setHtmlName(htmlName);
				if (iDocUploadFileService.updateById(entity)) {
					DocUploadFileOptHistory history = new DocUploadFileOptHistory();
					history.setId(UUIDUtils.getUuid());
					history.setFileId(id);
					history.setFileType("1");
					history.setOptType("2");
					history.setJsonData(GsonUtil.objectToJsonStr(entity));
					if (StringUtils.isBlank(userCode)) {
						history.setCreateBy(userId);
					} else {
						history.setCreateBy(userCode);
					}
					history.setCreateDate(new Date());
					iDocUploadFileOptHistoryService.insert(history);
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				} else {
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			} else {
				return new PublicResult<>(PublicResultConstant.ERROR, "请选择Excel、Word、Pdf文件", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage(), null);
		}
	}

	/**
	 * 删除回收站记录
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "删除回收站记录", notes = "删除回收站记录", httpMethod = "POST")
	@RequestMapping(value = "/deleteRecycleBinRecord", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public PublicResult<DocUploadFileOptHistory> deleteRecycleBinRecord(HttpServletRequest request,
			DocUploadFileOptHistory history) {
		PublicResultConstant publicResultConstant = PublicResultConstant.FAILED;
		try {
			User user = getLoginUser(request);
			history.setUpdateBy(user.getLoginName());
			Integer i = iDocUploadFileOptHistoryService.deleteRecycleBinRecord(history);
			return new PublicResult<>(PublicResultConstant.SUCCESS, String.valueOf(i), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(publicResultConstant, e.getMessage(), null);
		}
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "删除文件或文件夹", notes = "删除文件或文件夹", httpMethod = "POST")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public PublicResult<DocUploadFileOptHistory> deleteFile(HttpServletRequest request, String fileIds) {
		PublicResultConstant publicResultConstant = PublicResultConstant.FAILED;
		try {
			User user = getLoginUser(request);
			Integer i = iDocUploadFileOptHistoryService.deleteFile(fileIds, user.getLoginName());
			return new PublicResult<>(PublicResultConstant.SUCCESS, String.valueOf(i), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(publicResultConstant, e.getMessage(), null);
		}
	}

	/**
	 * 恢复文件或文件夹
	 * 
	 * @date 2018-12-17
	 * @author ZhangJiang
	 */
	@ApiOperation(value = "恢复文件或文件夹", notes = "恢复文件或文件夹", httpMethod = "POST")
	@RequestMapping(value = "/recoveryFile", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public PublicResult<DocUploadFileOptHistory> recoveryFile(HttpServletRequest request, String ids) {
		PublicResultConstant publicResultConstant = PublicResultConstant.FAILED;
		try {
			Integer i = iDocUploadFileOptHistoryService.recoveryFile(ids, getLoginUser(request).getLoginName());
			return new PublicResult<>(PublicResultConstant.SUCCESS, String.valueOf(i), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(publicResultConstant, e.getMessage(), null);
		}
	}

	/**
	 * @date 2018年9月25日 下午5:36:10
	 * @author ZhangJiang
	 * @todo 构建查询条件-以后扩展
	 */
	private EntityWrapper<DocUploadFileOptHistory> searchWrapper(HttpServletRequest request,
			DocUploadFileOptHistory entity) {
		EntityWrapper<DocUploadFileOptHistory> wrapper = new EntityWrapper<DocUploadFileOptHistory>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if (getLoginUser(request) != null && StringUtils.isNotBlank(getLoginUser(request).getId())) {
			if (!isAdmin(request))
				wrapper.eq("create_by", getLoginUser(request).getLoginName());
		}
		// 根据编码模糊查询
		if (entity.getId() != null && StringUtils.isNotBlank(String.valueOf(entity.getId()))) {
			wrapper.like("id", String.valueOf(entity.getId()));
		}
		// 根据文件类型 0-目录 1-文件模糊查询
		if (entity.getFileType() != null && StringUtils.isNotBlank(String.valueOf(entity.getFileType()))) {
			wrapper.like("file_type", String.valueOf(entity.getFileType()));
		}
		// 根据文件/目标编码模糊查询
		if (entity.getFileId() != null && StringUtils.isNotBlank(String.valueOf(entity.getFileId()))) {
			wrapper.like("file_id", String.valueOf(entity.getFileId()));
		}
		// 根据源数据json模糊查询
		if (entity.getJsonData() != null && StringUtils.isNotBlank(String.valueOf(entity.getJsonData()))) {
			wrapper.like("json_data", String.valueOf(entity.getJsonData()));
		}
		// 根据操作类型 0-预览 1-下载 2-修改 3-上传 4-删除 5-恢复模糊查询
		if (entity.getOptType() != null && StringUtils.isNotBlank(String.valueOf(entity.getOptType()))) {
			wrapper.like("opt_type", String.valueOf(entity.getOptType()));
		}
		// 根据备注信息模糊查询
		if (entity.getRemarks() != null && StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))) {
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
		// 根据创建者模糊查询
		if (entity.getCreateBy() != null && StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))) {
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
		// 根据创建时间模糊查询
		if (entity.getCreateDate() != null && StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))) {
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
		// 根据更新者模糊查询
		if (entity.getUpdateBy() != null && StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))) {
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
		// 根据更新时间模糊查询
		if (entity.getUpdateDate() != null && StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))) {
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
		// 根据删除标记（0：正常；1：删除）模糊查询
		if (entity.getDelFlag() != null && StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))) {
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
		wrapper.orderBy("update_date", false);
		return wrapper;
	}

	/**
	 * 数据查询
	 */
	public Object executeQuery(DocUploadFileOptHistory entity, Integer pageNum, Integer pageSize,
			HttpServletRequest request, String sqlAnd, Object... params) {
		try {
			EntityWrapper<DocUploadFileOptHistory> wrapper = this.searchWrapper(request, entity);
			wrapper.andNew(sqlAnd, params);
			PageHelper.startPage(pageNum, pageSize);
			int cu = (pageNum - 1) * pageSize;
			Page<DocUploadFileOptHistory> page = new Page<DocUploadFileOptHistory>(cu, pageSize);
			page = iDocUploadFileOptHistoryService.selectPage(page, wrapper);
			page.setTotal(iDocUploadFileOptHistoryService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage(), null);
		}
	}
}
