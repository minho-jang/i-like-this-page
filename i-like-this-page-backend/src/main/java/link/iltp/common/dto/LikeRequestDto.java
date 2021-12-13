package link.iltp.common.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class LikeRequestDto {
	private String url;
	private String uuid;
}
