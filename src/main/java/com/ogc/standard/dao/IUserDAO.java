/**
 * @Title IUserDAO.java 
 * @Package com.ibis.pz 
 * @Description 
 * @author miyb  
 * @date 2015-2-6 上午10:22:02 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.User;

/** 
 * 
 * @author: dl 
 * @since: 2018年8月20日 上午10:57:24 
 * @history:
 */
public interface IUserDAO extends IBaseDAO<User> {
    String NAMESPACE = IUserDAO.class.getName().concat(".");

    // 更改姓名
    public int updateRealName(User data);

    // 实名认证
    public int updateIdentity(User data);

    // 修改手续费率
    public int updateTradeRate(User data);

    // 设置支付密码
    public int updateTradePwd(User data);

    // 更改位置信息
    public int updateLocation(User data);

    // 更改负责区域
    public int updateRespArea(User data);

    // 设置登录密码
    public int updateLoginPwd(User data);

    // 更新手机号
    public int updateMobile(User data);

    // 更新邮箱
    public int updateEmail(User data);

    // 更新状态
    public int updateStatus(User data);

    // 更新昵称
    public int updateNickname(User data);

    // 更新头像
    public int updatePhoto(User data);

    // 更改推荐人
    public int updateReferee(User data);

    // 更新等级
    public int updateLevel(User data);

    // 更新用户手机号和真实信息
    public int updateSupple(User data);

    // 更新谷歌密码
    public int updateGoogleSecret(User data);

    // 最后一次登陆时间
    public int updateLastLogin(User data);

}
