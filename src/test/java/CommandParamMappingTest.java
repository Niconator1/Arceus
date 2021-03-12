import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.nico.util.CommandParamUtil;
import org.nico.util.Constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RunWith(Enclosed.class)
public class CommandParamMappingTest {

    @RunWith(Parameterized.class)
    public static class CommandParamMappingLightTest {
        private final String text;
        private final Map<String, Object> expected;

        public CommandParamMappingLightTest(String text, Map<String, Object> expected) {

            this.text = text;
            this.expected = expected;
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{{"", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "dim", "light.value", 1.0)}, {"Haus 2", Map.of("light.group", "Haus 2", "light.type", "dim", "light.value", 1.0)}, {"an", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "dim", "light.value", 1.0)}, {"Haus 2 an", Map.of("light.group", "Haus 2", "light.type", "dim", "light.value", 1.0)}, {"10 %", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "dim", "light.value", 0.1)}, {"Haus 2 10 %", Map.of("light.group", "Haus 2", "light.type", "dim", "light.value", 0.1)}, {"an 10 %", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "dim", "light.value", 0.1)}, {"Haus 2 an 10 %", Map.of("light.group", "Haus 2", "light.type", "dim", "light.value", 0.1)}, {"aus", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "off")}, {"Haus 2 aus", Map.of("light.group", "Haus 2", "light.type", "off")}, {"dimmen 10 %", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "dim", "light.value", 0.1)}, {"Haus 2 dimmen 10 %", Map.of("light.group", "Haus 2", "light.type", "dim", "light.value", 0.1)}, {"blau", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "color", "light.color", "blau")}, {"Haus 2 blau", Map.of("light.group", "Haus 2", "light.type", "color", "light.color", "blau")}, {"Farbe blau", Map.of("light.group", Constants.GROUP_DEFAULT, "light.type", "color", "light.color", "blau")}, {"Haus 2 Farbe blau", Map.of("light.group", "Haus 2", "light.type", "color", "light.color", "blau")}});
        }

        @Test
        public void lightTextShouldReturnProperMap() {
            System.out.println(text);
            Map<String, Object> resultMap = CommandParamUtil.mapLightParams(text);
            System.out.println(resultMap);
            expected.forEach((key, value) -> {
                Assert.assertTrue(resultMap.containsKey(key));
                Assert.assertEquals(value, resultMap.get(key));
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class CommandParamMappingLampTest {
        private final String text;
        private final Map<String, Object> expected;

        public CommandParamMappingLampTest(String text, Map<String, Object> expected) {

            this.text = text;
            this.expected = expected;
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{{"Lampe 2", Map.of("lamp.name", "Lampe 2", "lamp.type", "dim", "lamp.value", 1.0)}, {"Lampe 2 an", Map.of("lamp.name", "Lampe 2", "lamp.type", "dim", "lamp.value", 1.0)}, {"Lampe 2 10 %", Map.of("lamp.name", "Lampe 2", "lamp.type", "dim", "lamp.value", 0.1)}, {"Lampe 2 an 10 %", Map.of("lamp.name", "Lampe 2", "lamp.type", "dim", "lamp.value", 0.1)}, {"Lampe 2 aus", Map.of("lamp.name", "Lampe 2", "lamp.type", "off")}, {"Lampe 2 dimmen 10 %", Map.of("lamp.name", "Lampe 2", "lamp.type", "dim", "lamp.value", 0.1)}, {"Lampe 2 blau", Map.of("lamp.name", "Lampe 2", "lamp.type", "color", "lamp.color", "blau")}, {"Lampe 2 Farbe blau", Map.of("lamp.name", "Lampe 2", "lamp.type", "color", "lamp.color", "blau")}});
        }

        @Test
        public void lampTextShouldReturnProperMap() {
            System.out.println(text);
            Map<String, Object> resultMap = CommandParamUtil.mapLampParams(text);
            System.out.println(resultMap);
            expected.forEach((key, value) -> {
                Assert.assertTrue(resultMap.containsKey(key));
                Assert.assertEquals(value, resultMap.get(key));
            });
        }
    }

    @RunWith(Parameterized.class)
    public static class CommandParamMappingRadiatorTest {
        private final String text;
        private final Map<String, Object> expected;

        public CommandParamMappingRadiatorTest(String text, Map<String, Object> expected) {

            this.text = text;
            this.expected = expected;
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{{"aus", Map.of("radiator.group", Constants.GROUP_DEFAULT, "radiator.type", "temperature", "radiator.value", Constants.RADIATOR_TEMPERATURE_OFF)}, {"Haus 2 aus", Map.of("radiator.group", "Haus 2", "radiator.type", "temperature", "radiator.value", Constants.RADIATOR_TEMPERATURE_OFF)}, {"17 °", Map.of("radiator.group", Constants.GROUP_DEFAULT, "radiator.type", "temperature", "radiator.value", "17")}, {"Haus 2 17 °", Map.of("radiator.group", "Haus 2", "radiator.type", "temperature", "radiator.value", "17")}, {"an", Map.of("radiator.group", Constants.GROUP_DEFAULT, "radiator.type", "temperature", "radiator.value", Constants.RADIATOR_TEMPERATURE_DEFAULT)}, {"Haus 2 an", Map.of("radiator.group", "Haus 2", "radiator.type", "temperature", "radiator.value", Constants.RADIATOR_TEMPERATURE_DEFAULT)}, {"an 17 °", Map.of("radiator.group", Constants.GROUP_DEFAULT, "radiator.type", "temperature", "radiator.value", "17")}, {"Haus 2 an 17 °", Map.of("radiator.group", "Haus 2", "radiator.type", "temperature", "radiator.value", "17")}});
        }

        @Test
        public void radiatorTextShouldReturnProperMap() {
            System.out.println(text);
            Map<String, Object> resultMap = CommandParamUtil.mapRadiatorParams(text);
            System.out.println(resultMap);
            expected.forEach((key, value) -> {
                Assert.assertTrue(resultMap.containsKey(key));
                Assert.assertEquals(value, resultMap.get(key));
            });
        }
    }
}
