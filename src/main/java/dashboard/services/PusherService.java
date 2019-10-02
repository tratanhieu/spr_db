package dashboard.services;

import dashboard.generics.PusherResponse;

public interface PusherService {
    boolean createAction(String channelName, String response);
}
