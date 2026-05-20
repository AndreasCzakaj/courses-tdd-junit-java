package eu.binarystars.tdd.matchers

import org.apache.commons.io.IOUtils
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.charset.StandardCharsets
import java.nio.file.Files

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import static org.assertj.core.api.Assertions.assertThat

class FilesSpec extends Specification {

    @TempDir
    File tmpDir

    File newFile

    def setup() {
        assertThat(tmpDir).isDirectory()
        assertThat(tmpDir).exists()
        newFile = new File(tmpDir, "newFile.txt")
        assertThat(newFile).doesNotExist()

        def lines = ["The first line", "The second line"]
        Files.write(newFile.toPath(), lines, StandardCharsets.UTF_8)
    }

    @Ignore
    def "txt file should exist and contain first and second line"() {
        // "The first line"
        // "The second line"
        expect: true
    }

    def "json file should contain 1000 entries"() {
        given:
        def url = IOUtils.resourceToURL("/ppl.json")
        def file = new File(url.getFile())
        def json = Files.readString(file.toPath(), StandardCharsets.UTF_8)

        expect:
        assertThatJson(json).isArray().hasSize(1000)
    }
}
