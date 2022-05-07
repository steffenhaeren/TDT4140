import {UPDATE_PAGE, UpdatePageAction} from "./pageType"; 
  // Set up action for updating selected page 
  export const updatePage = (page: number): UpdatePageAction=> {
    return {
      type: UPDATE_PAGE,
      payload: page
    };
  };
