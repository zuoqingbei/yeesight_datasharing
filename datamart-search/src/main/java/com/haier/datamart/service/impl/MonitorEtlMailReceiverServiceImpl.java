package com.haier.datamart.service.impl;

import java.util.Date;
import java.util.List;

import com.haier.datamart.entity.MailReceiverManager;
import com.haier.datamart.entity.MailSettingInfo;
import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.haier.datamart.mapper.MonitorEtlMailReceiverMapper;
import com.haier.datamart.service.IMailReceiverManagerService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.service.IMonitorEtlMailReceiverService;
import com.haier.datamart.utils.UUIDUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
@Service
public class MonitorEtlMailReceiverServiceImpl extends ServiceImpl<MonitorEtlMailReceiverMapper, MonitorEtlMailReceiver> implements IMonitorEtlMailReceiverService {
	@Autowired
	private MonitorEtlMailReceiverMapper etlMailReceiverMapper;
	@Autowired
	private IMailReceiverManagerService iMailReceiverManagerService;
	@Autowired
	private IMailSettingInfoService iMailSettingInfoService;
	@Override
	public List<MonitorEtlMailReceiver> getEtlMailReceiver(
			MonitorEtlMailReceiver receiver) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.getEtlMailReceiver(receiver);
	}

	@Override
	public int insertEtlMailReceiver(MonitorEtlMailReceiver receiver) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.insertEtlMailReceiver(receiver);
	}

	@Override
	public int update(MonitorEtlMailReceiver receiver) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.updateByReceiveId(receiver);
	}

	@Override
	public int deleteByReceiveId(String receiveId) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.deleteByReceiveId(receiveId);
	}

	@Override
	public List<MonitorEtlMailReceiver> getEtlMailReceiver2Update(
			String receiveId) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.getEtlMailReceiver2Update(receiveId);
	}

	@Override
	public List<MonitorEtlMailReceiver> getByReceiveName(String receiveName) {
		// TODO Auto-generated method stub
		return etlMailReceiverMapper.getByReceiveName(receiveName);
	}
	/**
	 * 根据组编码receiverId删除对应的记录,并插入对应的实体
	 */
	@Override
	@Transactional
	public String alterMailReceiver(String mailReceiverManagerIds,  String receiveId,String userId
			,String receiveType, String mailId){
		//根据receiveId删除对应的实体
		if(!StringUtils.isEmpty(receiveId)) {
			Wrapper<MonitorEtlMailReceiver> wrapper =  new EntityWrapper<>();
			wrapper.eq("receive_id", receiveId);
			delete(wrapper);//物理删除
			//etlMailReceiverMapper.deleteByReceiveId(receiveId);
		}
		String newReceiveId = UUIDUtils.getUuid();
		//插入对应信息
		if(!StringUtils.isEmpty(mailReceiverManagerIds)) {
			String[] mailReceiverManagerIdArray = mailReceiverManagerIds.split(",");
			for (String mailReceiverManagerId : mailReceiverManagerIdArray) {
				Wrapper<MailReceiverManager> wrapper =  new EntityWrapper<>();
				wrapper.eq("id", mailReceiverManagerId);
				//获取对应实体
				MailReceiverManager mailReceiverManager = iMailReceiverManagerService.selectOne(wrapper);
				if(mailReceiverManager!=null) {
					//插入对应实体
					MonitorEtlMailReceiver mmr = new MonitorEtlMailReceiver();
					String mmrId = UUIDUtils.getUuid();
					mmr.setId(mmrId);
					mmr.setCreateBy(userId);
					mmr.setReceiveAddr(mailReceiverManager.getEmail());
					mmr.setCreateDate(new Date());
					mmr.setMailReceiverManagerId(mailReceiverManager.getId());
					mmr.setReceiveName(mailReceiverManager.getName());
					mmr.setReceiveId(newReceiveId);
					etlMailReceiverMapper.insert(mmr);
				}
				
			}
		}
		//更新邮件表
		MailSettingInfo mail = new MailSettingInfo();
		mail.setId(mailId); mail.setUpdateBy(userId);
		/**
		 * receiveType 0-短信收件人 1-邮件收件人 2-邮件抄送人
		 */
		switch (receiveType) {
		case "0":
			mail.setMessageReciverId(newReceiveId);
			break;
		case "1":
			mail.setMailReciverId(newReceiveId);
			break;
		case "2":
			mail.setMailCsReciverId(newReceiveId);
			break;
		default:
			break;
		}
		
		if(iMailSettingInfoService.saveOrUpdate(mail)) {
			return newReceiveId;
		}else {
			return "";
		}
		
	}

	 

}
