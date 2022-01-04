package link.iltp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
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
