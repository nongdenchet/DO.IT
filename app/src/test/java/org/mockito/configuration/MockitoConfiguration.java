package org.mockito.configuration;

/**
 * Created by nongdenchet on 2/13/16.
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public boolean enableClassCache() {
        return false;
    }
}
