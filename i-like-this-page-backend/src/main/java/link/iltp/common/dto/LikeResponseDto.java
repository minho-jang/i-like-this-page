package link.iltp.common.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class LikeResponseDto {
	private String url;
	private String uuid;
	private long likeCount;
	private boolean likeStatus;
}
