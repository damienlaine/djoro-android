package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Key;

public class SavingProposalSchedule extends SavingProposal {

    @Key
    private Program program;

    public SavingProposalSchedule(Program program) {
        super(10, SavingProposalStatus.PROPOSED, SavingProposalType.SCHEDULE);
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SavingProposalSchedule that = (SavingProposalSchedule) o;

        if (program != null ? !program.equals(that.program) : that.program != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (program != null ? program.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SavingProposalSchedule{" +
                "program=" + program +
                '}';
    }
}
