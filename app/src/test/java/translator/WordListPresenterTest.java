package translator;

import com.example.igor.translator.data.WordEntry;
import com.example.igor.translator.data.WordListDataSource;
import com.example.igor.translator.ui.List.WordListActivity;
import com.example.igor.translator.ui.List.WordListPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by igor on 20.08.16.
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class WordListPresenterTest {

    @Mock
    WordListActivity MockMainMvpView;
    @Mock
    WordListDataSource mMockDataSource;

    private WordListPresenter mMainPresenter;

    @Before
    public void setUp() {
        mMainPresenter = new WordListPresenter(mMockDataSource);
        mMainPresenter.setView(MockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadWordssReturnsWords() {
        List<WordEntry> ribots = new ArrayList<>(TestDataFactory.makeListRibots(10));
        doReturn(Observable.just(ribots))
                .when(mMockDataSource)
                .getWords();

        mMainPresenter.start();
        verify(MockMainMvpView).setWorldList(ribots);
        verify(MockMainMvpView, never()).showError();
    }

    @Test
    public void loadWordsReturnsEmptyList() {
        List emptyList = Collections.emptyList();
        doReturn(Observable.just(emptyList))
                .when(mMockDataSource)
                .getWords();

        mMainPresenter.start();
        verify(MockMainMvpView).setWorldList(emptyList);
        verify(MockMainMvpView).showEmptyListPlaceHolder();
        verify(MockMainMvpView, never()).showError();
    }

    @Test
    public void loadWordssFails() {
        doReturn(Observable.error(new RuntimeException()))
                .when(mMockDataSource)
                .getWords();

        mMainPresenter.start();
        verify(MockMainMvpView).showError();
        verify(MockMainMvpView, never()).showEmptyListPlaceHolder();
        verify(MockMainMvpView, never()).setWorldList(anyListOf(WordEntry.class));
    }
}
