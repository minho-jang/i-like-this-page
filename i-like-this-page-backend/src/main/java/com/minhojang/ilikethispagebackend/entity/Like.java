package com.minhojang.ilikethispagebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeId;

	@Column(name = "uuid", length = 400)
	private String uuid;

	@Column(name = "url", length = 2100)
	private String url;

	@CreationTimestamp
	private LocalDateTime createAt;
}
