package pl.nqriver.homebudget.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AssetsDto {
    List<Integer> assets;
}
