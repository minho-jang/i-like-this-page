package link.iltp.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeResponseDto {
	private String url;
	private String uuid;
	private long likeCount;
	private boolean likeStatus;
}
