import SwiftUI
import common

struct ContentView: View {
    
    @EnvironmentObject var controller : AppController

    var body: some View {
        
        GeometryReader{ metric in
            
            VStack{
            
                if !(controller.hasNetworkAccess ?? true) {
                    HStack(alignment: .center){
                        Spacer()
                        Image(systemName: "icloud.slash")
                        Text("No Internet Connection")
                        Spacer()
                    }
                    .padding(.vertical, 5)
                    .foregroundColor(Color("Primary"))
                    .animation(.linear, value: !(controller.hasNetworkAccess ?? true))
                }
                        
                
                ZStack{
                    if controller.hasOnBoarded == true {
                        if controller.isSignedIn == true {
                            if controller.hasSetup == true {
                                NavigationView{ HomeScreen() }
                            } else {
                                AccountScreen(isSettingUp: true)
                            }
                        } else {
                            MainScreen()
                        }
                    } else {
                        OnBoardingScreen { controller.setUserHasOnBoarded() }
                    }
                    
                    if controller.isLoading {
                        LoadingDialog().ignoresSafeArea()
                    }
                    
                }
                
                Spacer()
                
            }.frameLayout(metric: metric)
                .alert(isPresented: $controller.isShowingDialog, content: {
                    Alert(
                        title: Text(controller.dialog?.0 ?? "title"),
                        message: Text(controller.dialog?.1 ?? "message"),
                        dismissButton: .cancel({controller.hideDialog()})
                    )
                })
            
        }.onAppear(perform: monitorNetworkState)
        
    }
    
    private func monitorNetworkState(){
        NetworkMonitor.shared.start()
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
