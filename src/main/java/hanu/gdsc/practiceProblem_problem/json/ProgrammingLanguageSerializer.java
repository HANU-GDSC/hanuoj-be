package hanu.gdsc.practiceProblem_problem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import hanu.gdsc.core_problem.domains.ProgrammingLanguage;

import java.io.IOException;

public class ProgrammingLanguageSerializer  extends StdSerializer<ProgrammingLanguage> {
    protected ProgrammingLanguageSerializer() {
        this(null);
    }


    public ProgrammingLanguageSerializer(Class<ProgrammingLanguage> t) {
        super(t);
    }

    @Override
    public void serialize(ProgrammingLanguage p, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(p.toString());
    }
}
