package eu.binarystars.tdd.matchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class FilesTest {
    File newFile;

    @BeforeEach
    void init(@TempDir File tmpDir) throws Throwable {
        assertThat(tmpDir).isDirectory();
        assertThat(tmpDir).exists();
        newFile = new File(tmpDir, "newFile.txt");
        assertThat(newFile).doesNotExist();

        List<String> lines = Arrays.asList("The first line", "The second line");
        Files.write(newFile.toPath(), lines, StandardCharsets.UTF_8);
    }

    @Test
    @Disabled
    void txtFileShouldExistAndContainFirstAndSecondLine() throws Throwable {
        // "The first line"
        // "The second line"
    }

    @Test
    void jsonFile() throws Throwable {
        URL url = IOUtils.resourceToURL("/ppl.json");
        File file = new File(url.getFile());
        assertThat(file.exists()).isTrue();
        String json = Files.readString(file.toPath(), StandardCharsets.UTF_8);

        assertThatJson(json).isArray().hasSize(1000);
    }

}
