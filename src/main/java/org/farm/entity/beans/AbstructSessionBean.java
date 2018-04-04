package org.farm.entity.beans;

import javax.faces.bean.ManagedProperty;

public abstract class AbstructSessionBean {

	@ManagedProperty(value = "#{loginAuthenticationBean}")
	protected LoginAuthenticationBean loginAuthenticationBean;

	public LoginAuthenticationBean getLoginAuthenticationBean() {
		return loginAuthenticationBean;
	}

	public void setLoginAuthenticationBean(LoginAuthenticationBean loginAuthenticationBean) {
		this.loginAuthenticationBean = loginAuthenticationBean;
	}

}
