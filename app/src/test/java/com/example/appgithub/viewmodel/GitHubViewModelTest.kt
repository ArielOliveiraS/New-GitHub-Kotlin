package com.example.appgithub.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appgithub.data.repository.RepositoryViewContract
import com.example.appgithub.model.GitHubResponse
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GitHubViewModelTest {

    @Rule
    @JvmField
    val rule : TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryViewcontract: RepositoryViewContract

    @Mock
    lateinit var repositories: GitHubResponse

    lateinit var viewModel: GitHubViewModel

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}  //codigo rodando em uma thread e o teste em outra, teste quebrando
        MockitoAnnotations.initMocks(this)  //inicializa todos com anotatio @mock
        viewModel = instantiateViewModel()
    }

    @Test
    fun test_getAllRepos_success() {
        //Given
        val viewModelSpy = Mockito.spy(viewModel) //spy é tipo um mock mas ele executa o método, entra nele e executa, tem q passar um objeto real, nao um mock
        val listItems = Single.just(GitHubResponse(1, false, arrayListOf())) //objeto q será retornado pelo repositório
        var items = GitHubResponse(1, false, arrayListOf())
        listItems.subscribe { result -> items = result }

        //definindo açoes para os métodos do getAllMovies
        Mockito.`when`(repositoryViewcontract.getRepositories(1)).thenReturn(listItems) //retorna uma lista
        Mockito.doNothing().`when`(viewModelSpy).setItemList(items) //nao retorna nada pq é um void
        Mockito.doNothing().`when`(viewModelSpy).setLoading(ArgumentMatchers.anyBoolean())

        //Act
        viewModelSpy.getAllRepositories(1)

        //Assert
        Mockito.verify(viewModelSpy, Mockito.times(1)).getAllRepositories(1)
        Mockito.verify(viewModelSpy, Mockito.never()).setError(false)
        Mockito.verify(repositoryViewcontract, Mockito.times(1)).getRepositories(1)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setLoading(true)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setLoading(false)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setItemList(items)
    }

    @Test
    fun test_getAllRepos_error() {
        //Given
        val errorMessage = "Error Message"
        val throwable = Throwable(errorMessage)
        val viewModelSpy = Mockito.spy(viewModel)

        Mockito.`when`(repositoryViewcontract.getRepositories(1)).thenReturn(Single.error(throwable))
        Mockito.doNothing().`when`(viewModelSpy).setLoading(ArgumentMatchers.anyBoolean())
        Mockito.doNothing().`when`(viewModelSpy).setError(false)

        //Act
        viewModelSpy.getAllRepositories(1)

        //Assert
        Mockito.verify(viewModelSpy, Mockito.times(1)).getAllRepositories(1)
        Mockito.verify(viewModelSpy, Mockito.never()).setItemList(ArgumentMatchers.any())
        Mockito.verify(repositoryViewcontract, Mockito.times(1)).getRepositories(1)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setLoading(true)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setLoading(false)
        Mockito.verify(viewModelSpy, Mockito.times(1)).setError(true)
    }

    @Test
    fun test_setItemList() {
        viewModel.setItemList(repositories)

        Assert.assertEquals(repositories, viewModel.repositorytResult.value)
    }

    @Test
    fun test_setLoading() {
        viewModel.setLoading(true)

        Assert.assertTrue(viewModel.loadingResult.value ?: false)
    }

    private fun instantiateViewModel(): GitHubViewModel {
        return GitHubViewModel(repositoryViewcontract) //chamando o mock ao inicializar o view model
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(repositoryViewcontract, repositories) //verifica interaçoes dos mocks
    }
}