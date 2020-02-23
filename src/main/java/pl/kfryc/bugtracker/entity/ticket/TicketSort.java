package pl.kfryc.bugtracker.entity.ticket;

import java.util.Comparator;

public class TicketSort implements Comparator<Ticket>
{
    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.getId() - o2.getId();
    }
}