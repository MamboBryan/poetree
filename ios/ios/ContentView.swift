import SwiftUI
import common

struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
		Text("Poetree iOS")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
