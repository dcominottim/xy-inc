package com.xy.poi;

import com.xy.poi.config.SpringProfiles;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = { SpringProfiles.TESTING })
public class ContractTestConfig {
}
