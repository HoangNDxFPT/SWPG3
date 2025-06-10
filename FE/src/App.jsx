import React from 'react';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { store, persistor } from './redux/store';
import HomePage from './member/page/HomePage';
import LoginPage from './pages/login';
import RegisterPage from './pages/register';
import UserProfilePage from './member/page/UserProfilePage';
import Servey from './member/page/Servey';
import CouresListPage from './member/page/CouresListPage';
import ConsultantList from './member/page/ConsultantList';
function RequireAdmin({ children }) {
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  if (!user || user.role_id !== 1) return <LoginPage />;
  return children;
}

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
  },
  {
    path: "/profile",
    element: <UserProfilePage />,
  },
  {
    path: "/servey",
    element: <Servey />,
  },
  {
    path: "/courseList",
    element: <CouresListPage />,
  },

 {
    path: "/consultantList",
    element: <ConsultantList />,
  },

]);

function App() {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <RouterProvider router={router} />
      </PersistGate>
    </Provider>
  );
}

export default App;
