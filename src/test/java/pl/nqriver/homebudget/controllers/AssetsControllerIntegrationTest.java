package pl.nqriver.homebudget.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser()
class AssetsControllerIntegrationTest {

    public static final BigDecimal VALID_ASSET_AMOUNT = BigDecimal.valueOf(23);
    public static final AssetCategory VALID_ASSET_CATEGORY = AssetCategory.RENT;
    public static final AssetCategory INVALID_ASSET_CATEGORY = null;
    public static final BigDecimal INVALID_ASSET_AMOUNT = BigDecimal.valueOf(-1);
    public static final Instant INVALID_ASSET_DATE = null;
    public static final Instant VALID_INCOME_DATE = Instant.now();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void whenPostRequestToAssetAndInvalidAmount_thenCorrectResponse() throws Exception {
        //given
        AssetDto invalidAssetDto = AssetDto.builder()
                .amount(INVALID_ASSET_AMOUNT)
                .incomeDate(VALID_INCOME_DATE)
                .category(VALID_ASSET_CATEGORY)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/assets/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAssetDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        String mvcResponse = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResponse)
                .containsIgnoringCase("errors")
                .containsIgnoringCase("amount");
    }

    @Test
    void whenPostRequestToAssetAndInvalidCategory_thenCorrectResponse() throws Exception {
        //given
        AssetDto invalidAssetDto = AssetDto.builder()
                .amount(VALID_ASSET_AMOUNT)
                .incomeDate(VALID_INCOME_DATE)
                .category(INVALID_ASSET_CATEGORY)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/assets/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAssetDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        String mvcResponse = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResponse)
                .containsIgnoringCase("errors")
                .containsIgnoringCase("category");
    }

    @Test
    void whenPostRequestToAssetAndInvalidMultipleFields_thenCorrectResponse() throws Exception {
        //given
        AssetDto invalidAssetDto = AssetDto.builder()
                .amount(INVALID_ASSET_AMOUNT)
                .incomeDate(INVALID_ASSET_DATE)
                .category(INVALID_ASSET_CATEGORY)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/assets/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAssetDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        String mvcResponse = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResponse)
                .containsIgnoringCase("errors")
                .containsIgnoringCase("category")
                .containsIgnoringCase("incomeDate")
                .containsIgnoringCase("amount");
    }

//    @Test
//    void whenPostRequestToAssetAndValidAsset_thenCorrectResponse() throws Exception {
//        //given
//        AssetDto invalidAssetDto = AssetDto.builder()
//                .amount(BigDecimal.valueOf(21))
//                .incomeDate(Instant.now())
//                .category(AssetCategory.RENT)
//                .build();
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.post("/assets/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidAssetDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON));
//    }
}