package heyrin.service;

import heyrin.repository.HrConfigurationRepository;
import heyrin.repository.entity.HrConfigruation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HrConfigurationService {
    @Autowired
    private HrConfigurationRepository hrConfigurationRepository;

    public String getConfiguration(String configKey) {
        HrConfigruation oneByKey = hrConfigurationRepository.findOneByKey(configKey);
        return oneByKey.getValue();
    }

}
