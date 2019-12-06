package Tasks;

import components.ViewModel;
import java.util.TimerTask;

public class KeepTimeTask extends TimerTask {

    private ViewModel m_viewModel;
    
    public KeepTimeTask(ViewModel viewModel){
        m_viewModel = viewModel;
    }
    
    @Override
    public void run() {
        m_viewModel.UpdateGameTime();
    }

}
