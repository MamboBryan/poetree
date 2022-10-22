import SwiftUI
import common

struct ContentView: View {
    
    @StateObject var viewModel : ContentViewModel = ContentViewModel()
    
    var isOnBoarded : Bool = false
    let isAuthenticated: Bool = true
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
                    if isOnBoarded == true {
                        if isAuthenticated == true {
                            if userHasSetup == true {
                                NavigationView{ HomeScreen() }
                            } else {
                                AccountScreen(isSettingUp: true)
                            }
                        } else {
                            MainScreen()
                        }
                    } else {
                        OnBoardingScreen{
                            // TODO : update isOnboarded state to true
                        }
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
