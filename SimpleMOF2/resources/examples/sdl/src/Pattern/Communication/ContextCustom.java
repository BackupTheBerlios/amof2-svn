package Pattern.Communication;

public class ContextCustom extends ContextDlg {
    @Override
    public void update() {
        Event nextEvent = self.getPendingEvents().iterator().next();
        self.getPendingEvents().remove(nextEvent);

        loop:
        for (Process process: self.getProcess()) {
            for (Listener input: process.getInput()) {
                if (input.listensTo(nextEvent)) {
                    process.consume(input, nextEvent);
                    nextEvent = null;
                    break loop;
                }
            }
        }

        if (nextEvent != null) {
            System.out.println("droped event " + nextEvent);
            nextEvent.metaDelete();
        }
    }
}
