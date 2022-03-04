package hanu.gdsc.coreProblem.domains;


import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.domains.IdentitifedDomainObject;

public class MemoryLimit extends IdentitifedDomainObject{
    private ProgrammingLanguage programmingLanguage;
    private KB memoryLimit;

    public MemoryLimit(Id id, long version, ProgrammingLanguage programmingLanguage, KB memoryLimit) {
        super(id, version);
        this.programmingLanguage = programmingLanguage;
        this.memoryLimit = memoryLimit;
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public KB getMemoryLimit() {
        return memoryLimit;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public void setMemoryLimit(KB memoryLimit) {
        this.memoryLimit = memoryLimit;
    }
}
