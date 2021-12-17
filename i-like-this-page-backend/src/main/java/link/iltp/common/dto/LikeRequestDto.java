package link.iltp.common.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
public class LikeRequestDto {
	private String url;
	private String uuid;
}
