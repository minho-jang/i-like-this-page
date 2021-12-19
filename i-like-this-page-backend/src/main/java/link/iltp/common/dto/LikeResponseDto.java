package link.iltp.common.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
public class LikeResponseDto {
	private String url;
	private String uuid;
	private long likeCount;
	private boolean likeStatus;
}
