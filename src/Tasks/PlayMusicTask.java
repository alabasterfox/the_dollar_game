package Tasks;

import components.DJ;
import components.ViewModel;

public class PlayMusicTask implements Runnable {

    private DJ m_dj = new DJ();
    private ViewModel m_viewModel;
    
    public PlayMusicTask(ViewModel viewModel){
        m_viewModel = viewModel;
    }
    
    @Override
    public void run() {
        m_dj.PlayMusic();
    }

}
