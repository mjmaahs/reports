@ExtendWith(MockitoExtension.class)
class HoldDtoToIncatHoldDtoMapperTest {

    @Mock
    private InstantToLongMapper instantToLongMapper;

    @InjectMocks
    private HoldDtoToIncatHoldDtoMapperImpl dtoMapper;

    @Test
    void test_map_when_holdDto_is_null() {
        HoldDto holdDto = null;
        IncatHoldDto actual = dtoMapper.map(holdDto);
        assertThat(actual).isNull();
    }

    @Test
    void test_map_when_holdDto_has_empty_holds() {
        HoldDto holdDto = new HoldDto();
        holdDto.setHolds(Collections.emptyList());
        IncatHoldDto actual = dtoMapper.map(holdDto);
        assertThat(actual).isNotNull();
        assertThat(actual.getData()).isEmpty();
    }

    @Test
    void test_map_when_holdDto_has_data() {
        HoldDetailDto holdDetailDto = new HoldDetailDto();
        holdDetailDto.setId("id");
        holdDetailDto.setAuthorizationReferenceId("authRef");
        holdDetailDto.setAmount(new Money(BigDecimal.TEN, "USD"));
        holdDetailDto.setAccountNumber("accNum");
        holdDetailDto.setSourceCurrencyRate(new BigDecimal("1.1"));
        holdDetailDto.setSettlementCurrencyRate(new BigDecimal("1.2"));
        holdDetailDto.setVatRate(new BigDecimal("1.3"));
        holdDetailDto.setTags(List.of("tag1", "tag2"));
        holdDetailDto.setEnrichments(Map.of("key1", "value1"));

        HoldDto holdDto = new HoldDto();
        holdDto.setHolds(List.of(holdDetailDto));

        given(instantToLongMapper.map(any())).willReturn(1L);

        IncatHoldDto actual = dtoMapper.map(holdDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getData()).hasSize(1);
        IncatHoldDetailDto incatHoldDetailDto = actual.getData().get(0);
        assertThat(incatHoldDetailDto.getId()).isEqualTo("id");
        assertThat(incatHoldDetailDto.getAuthorizationReferenceId()).isEqualTo("authRef");
        assertThat(incatHoldDetailDto.getAmount()).isEqualTo(new Money(BigDecimal.TEN, "USD"));
        assertThat(incatHoldDetailDto.getAccountNumber()).isEqualTo("accNum");
        assertThat(incatHoldDetailDto.getSourceCurrencyRate()).isEqualTo(new BigDecimal("1.1"));
        assertThat(incatHoldDetailDto.getSettlementCurrencyRate()).isEqualTo(new BigDecimal("1.2"));
        assertThat(incatHoldDetailDto.getVatRate()).isEqualTo(new BigDecimal("1.3"));
        assertThat(incatHoldDetailDto.getTags()).containsExactlyInAnyOrder("tag1", "tag2");
        assertThat(incatHoldDetailDto.getEnrichments()).containsExactlyInAnyOrderEntriesOf(Map.of("key1", "value1"));
    }

    @Test
    void test_map_when_holdDetailDto_is_null() {
        HoldDetailDto holdDetailDto = null;
        IncatHoldDetailDto actual = dtoMapper.map(holdDetailDto);
        assertThat(actual).isNull();
    }

    @Test
    void test_map_when_holdDetailDto_has_data() {
        HoldDetailDto holdDetailDto = new HoldDetailDto();
        holdDetailDto.setId("id");
        holdDetailDto.setAuthorizationReferenceId("authRef");
        holdDetailDto.setAmount(new Money(BigDecimal.TEN, "USD"));
        holdDetailDto.setAccountNumber("accNum");
        holdDetailDto.setSourceCurrencyRate(new BigDecimal("1.1"));
        holdDetailDto.setSettlementCurrencyRate(new BigDecimal("1.2"));
        holdDetailDto.setVatRate(new BigDecimal("1.3"));
        holdDetailDto.setTags(List.of("tag1", "tag2"));
        holdDetailDto.setEnrichments(Map.of("key1", "value1"));

        given(instantToLongMapper.map(any())).willReturn(1L);

        IncatHoldDetailDto actual = dtoMapper.map(holdDetailDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo("id");
        assertThat(actual.getAuthorizationReferenceId()).isEqualTo("authRef");
        assertThat(actual.getAmount()).isEqualTo(new Money(BigDecimal.TEN, "USD"));
        assertThat(actual.getAccountNumber()).isEqualTo("accNum");
        assertThat(actual.getSourceCurrencyRate()).isEqualTo(new BigDecimal("1.1"));
        assertThat(actual.getSettlementCurrencyRate()).isEqualTo(new BigDecimal("1.2"));
        assertThat(actual.getVatRate()).isEqualTo(new BigDecimal("1.3"));
        assertThat(actual.getTags()).containsExactlyInAnyOrder("tag1", "tag2");
        assertThat(actual.getEnrichments()).containsExactlyInAnyOrderEntriesOf(Map.of("key1", "value1"));
    }

    @Test
    void test_holdDetailDtoListToIncatHoldDetailDtoList_when_list_is_null() {
        List<HoldDetailDto> list = null;
        List<IncatHoldDetailDto> actual = dtoMapper.holdDetailDtoListToIncatHoldDetailDtoList(list);
        assertThat(actual).isNull();
    }

    @Test
    void test_holdDetailDtoListToIncatHoldDetailDtoList_when_list_has_data() {
        HoldDetailDto holdDetailDto = new HoldDetailDto();
        holdDetailDto.setId("id");
        holdDetailDto.setAuthorizationReferenceId("authRef");
        holdDetailDto.setAmount(new Money(BigDecimal.TEN, "USD"));
        holdDetailDto.setAccountNumber("accNum");

        List<HoldDetailDto> list = List.of(holdDetailDto);

        given(instantToLongMapper.map(any())).willReturn(1L);

        List<IncatHoldDetailDto> actualList = dtoMapper.holdDetailDtoListToIncatHoldDetailDtoList(list);

        assertThat(actualList).hasSize(1);

        IncatHoldDetailDto first = actualList.get(0);
        assertThat(first.getId()).isEqualTo("id");
        assertThat(first.getAuthorizationReferenceId()).isEqualTo("authRef");
        assertThat(first.getAmount()).isEqualTo(new Money(BigDecimal.TEN, "USD"));
        assertThat(first.getAccountNumber()).isEqualTo("accNum");
    }
}
