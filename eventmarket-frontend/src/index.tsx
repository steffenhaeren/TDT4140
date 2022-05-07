import React from 'react';
import ReactDOM from 'react-dom';
import './style/style.css';
import App from './App';
import {
  ApolloClient,
  InMemoryCache,
  ApolloProvider,
  createHttpLink,
} from "@apollo/client";
import allReducers from './redux';
import { createStore, compose } from 'redux';
import { Provider } from 'react-redux';
import { setContext } from '@apollo/client/link/context';
import {HashRouter, Route, Routes} from 'react-router-dom';
import Explore from './pages/Explore';
import CreateEvent from './pages/CreateEvent';
import Profile from './pages/Profile';
import SignUp from './pages/SignUp';
import LogIn from './pages/LogIn';
import Event from './pages/Event'

//import reportWebVitals from './reportWebVitals';
const authLink = setContext((_:any, { headers}) => {
  // get the jwt token from sessionStorage if it exists
  const token = sessionStorage.getItem('jwt');
  // return the headers to the context so httpLink can read them
  return {
    headers: {
      ...headers,
      authorization: token ? token : "",
    }
  }
});
const httpLink = createHttpLink({
  uri: 'http://localhost:3000/',
});

const client = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache()
});

declare global {
  interface Window {
    __REDUX_DEVTOOLS_EXTENSION__?: typeof compose;
  }
};

// Set up redux store
const store = createStore(allReducers,
  // This is just for getting access to the redux devtool in chrome
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

//Wrap the whole application inside apollo clien provider and provider from react-redux
ReactDOM.render(
  <ApolloProvider client={client}>
    <Provider store={store}>
      <React.StrictMode>
        <HashRouter>
          <Routes>
            <Route path="/" element={<App />} />
            <Route path="/pages/login" element={<LogIn />} />
            <Route path="/index.html" element={<App />} />
            <Route path="/pages/explore" element={<Explore />} />
            <Route path="/pages/createevent" element={<CreateEvent />} />
            <Route path="/pages/event" element={<Event />} />
            <Route path="/pages/profile" element={<Profile />} />
            <Route path="/pages/signup" element={<SignUp />} />
          </Routes>
        </HashRouter>
      </React.StrictMode>
    </Provider>
  </ApolloProvider>,
  document.getElementById('root')
);
// vim:sw=2
