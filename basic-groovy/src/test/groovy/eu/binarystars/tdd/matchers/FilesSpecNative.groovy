package eu.binarystars.tdd.matchers

import groovy.json.JsonSlurper
import org.apache.commons.io.IOUtils
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.charset.StandardCharsets
import java.nio.file.Files

class FilesSpecNative extends Specification {

    @TempDir
    File tmpDir

    File newFile

    def setup() {
        assert tmpDir.isDirectory()
        assert tmpDir.exists()
        newFile = new File(tmpDir, "newFile.txt")
        assert !newFile.exists()

        def lines = ["The first line", "The second line"]
        Files.write(newFile.toPath(), lines, StandardCharsets.UTF_8)
    }

    def "txt file should exist and contain first and second line"() {
        expect:
        newFile.exists()
        Files.readAllLines(newFile.toPath(), StandardCharsets.UTF_8) == ["The first line", "The second line"]
    }

    def "json file should contain 1000 entries"() {
        given:
        def url = IOUtils.resourceToURL("/ppl.json")
        def people = new JsonSlurper().parse(new File(url.getFile()))

        expect:
        people.size() == 1000
    }
}
