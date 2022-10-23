import SwiftUI
import common

struct ContentView: View {
    
    @StateObject var viewModel : ContentViewModel = ContentViewModel()

    let isAuthenticated: Bool = false
    let userHasSetup : Bool = false
    
    var body: some View {
        
        GeometryReader{ metric in
            
            VStack{
                
                HStack(alignment: .center){
                    Spacer()
                    Image(systemName: "icloud.slash")
                    Text("No Internet Connection")
                    Spacer()
                }
                .padding(.vertical, 5)
                .background(Color("Primary"))
                .foregroundColor(Color("OnPrimary"))
                
                ZStack{
                    if viewModel.hasOnBoarded == true {
                        if viewModel.isSignedIn == true {
                            if viewModel.hasSetup == true {
                                NavigationView{ HomeScreen() }
                            } else {
                                AccountScreen(isSettingUp: true)
                            }
                        } else {
                            MainScreen()
                        }
                    } else {
                        OnBoardingScreen { viewModel.setUserHasOnBoarded() }
                    }
                }
                
                Spacer()
                
            }.frameLayout(metric: metric)
        }
        
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
