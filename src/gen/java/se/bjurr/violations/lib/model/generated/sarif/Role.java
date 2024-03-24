
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    ANALYSIS_TARGET("analysisTarget"),
    ATTACHMENT("attachment"),
    RESPONSE_FILE("responseFile"),
    RESULT_FILE("resultFile"),
    STANDARD_STREAM("standardStream"),
    TRACED_FILE("tracedFile"),
    UNMODIFIED("unmodified"),
    MODIFIED("modified"),
    ADDED("added"),
    DELETED("deleted"),
    RENAMED("renamed"),
    UNCONTROLLED("uncontrolled"),
    DRIVER("driver"),
    EXTENSION("extension"),
    TRANSLATION("translation"),
    TAXONOMY("taxonomy"),
    POLICY("policy"),
    REFERENCED_ON_COMMAND_LINE("referencedOnCommandLine"),
    MEMORY_CONTENTS("memoryContents"),
    DIRECTORY("directory"),
    USER_SPECIFIED_CONFIGURATION("userSpecifiedConfiguration"),
    TOOL_SPECIFIED_CONFIGURATION("toolSpecifiedConfiguration"),
    DEBUG_OUTPUT_FILE("debugOutputFile");
    private final String value;
    private final static Map<String, Role> CONSTANTS = new HashMap<String, Role>();

    static {
        for (Role c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }

    public static Role fromValue(String value) {
        Role constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
