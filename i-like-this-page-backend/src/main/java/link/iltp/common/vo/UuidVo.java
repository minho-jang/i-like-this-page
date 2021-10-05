package link.iltp.common.vo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UuidVo {
	private String uuid;
	private boolean isNew;

	public void setUuid(String uuid) {
		this.uuid = uuid;
		this.isNew = false;
	}

	public void setNewUuid(String uuid) {
		this.uuid = uuid;
		this.isNew = true;
	}

	public String getUuid() {
		return uuid;
	}

	public boolean isNew() {
		return isNew;
	}

	@PostConstruct
	public void init() {
		log.info("Create UuidVo request scoped");
	}

	@PreDestroy
	public void destroy() {
		log.info("Destroy UuidVo request scoped");
	}
}
