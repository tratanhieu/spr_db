package dashboard.services.implement;

import com.pusher.rest.Pusher;
import com.pusher.rest.data.Result;
import dashboard.constants.PusherConstants;
import dashboard.generics.PusherResponse;
import dashboard.services.PusherService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PusherServiceImpl implements PusherService {

    private Pusher pusher;

    @PostConstruct
    public void configure() {
        pusher = new Pusher(
                PusherConstants.PUSHER_APP_ID,
                PusherConstants.PUSHER_APP_KEY,
                PusherConstants.PUSHER_APP_SECRET
        );
        pusher.setCluster(PusherConstants.PUSHER_APP_CLUSTER);
        pusher.setEncrypted(true);
    }

    @Override
    public boolean createAction(String channelName, String response) {
        Result res = pusher.trigger(PusherConstants.PUSHER_APP_CHANEL_NAME, channelName, response);
        return res.getHttpStatus() == 200;
    }
}
