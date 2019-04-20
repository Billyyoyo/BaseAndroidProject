package fun.flyee.sunshine4u.android.events;

import fun.flyee.sunshine4u.android.models.Note;

public class NoteEvent {

    public final static int ACTION_ADD = 0;
    public final static int ACTION_UPDATE = 1;
    public final static int ACTION_DELETE = 2;

    public int action;
    public Note note;

    public NoteEvent(int action, Note note) {
        this.action = action;
        this.note = note;
    }
}
