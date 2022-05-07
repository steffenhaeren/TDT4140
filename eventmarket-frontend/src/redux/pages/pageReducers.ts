import { UPDATE_PAGE,UpdatePageAction } from "./pageType";
const initialPagePayload={
  payload: 1
}
// Reducer for updating the state with new page
export const pageReducer = (
    state = initialPagePayload,
    action: UpdatePageAction,
  )=> {
    switch (action.type) {
      case UPDATE_PAGE:
        return {
          payload: action.payload
        };
      default:
        return state;
    }
  };
