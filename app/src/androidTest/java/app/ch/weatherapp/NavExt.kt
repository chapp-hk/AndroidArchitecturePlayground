package app.ch.weatherapp

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController

inline fun <reified T : Fragment> launchNavFragment(
    graphResId: Int,
    navController: TestNavHostController,
    crossinline action: Fragment.() -> Unit = {}
) {
    launchFragmentInHiltContainer<T> {
        navController.setGraph(graphResId)
        viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
            if (viewLifecycleOwner != null) {
                // The fragment’s view has just been created
                Navigation.setViewNavController(requireView(), navController)
            }
        }

        action()
    }
}
