package com.minhojang.ilikethispagebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "likes")
public class Like {

	@Id
	@GeneratedValue
	private Long likeId;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "url")
	private String url;

	@CreationTimestamp
	private LocalDateTime createAt;
}
