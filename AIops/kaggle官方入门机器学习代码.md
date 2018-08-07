

### 1 pandas用法介绍

    import pandas as pd

    main_file_path = '../input/house-prices-advanced-regression-techniques/train.csv' # this is the path to the Iowa data that you will use
    data = pd.read_csv(main_file_path)

    # Run this code block with the control-enter keys on your keyboard. Or click the blue botton on the left
    # print('Some output from running this cell')
    print(data.describe())


![](http://ww1.sinaimg.cn/large/005N2p5vly1fu0wflmnpsj31160fqwhb.jpg)

### 2 使用回归决策树预测房价

    import pandas as pd
    from sklearn.tree import DecisionTreeRegressor

    main_file_path = '../input/house-prices-advanced-regression-techniques/train.csv' # this is the path to the Iowa data that you will use
    data = pd.read_csv(main_file_path)

    columns_of_interest = ['LotArea','YearBuilt','1stFlrSF','2ndFlrSF','FullBath','BedroomAbvGr','TotRmsAbvGrd']
    X = data[columns_of_interest]
    y = data.SalePrice
    model = DecisionTreeRegressor()
    model.fit(X, y)
                        
    print("Making predictions for the following 5 houses:")
    print(X.head())
    print("The predictions are")
    print(model.predict(X.head()))

    # print(data.describe())
    # print(data.columns)


![](http://ww1.sinaimg.cn/large/005N2p5vly1fu0wihkz8bj31a00fg0uw.jpg)


### 3 MAE估计误差

    import pandas as pd
    from sklearn.tree import DecisionTreeRegressor
    from sklearn.metrics import mean_absolute_error

    main_file_path = '../input/house-prices-advanced-regression-techniques/train.csv' # this is the path to the Iowa data that you will use
    data = pd.read_csv(main_file_path)

    columns_of_interest = ['LotArea','YearBuilt','1stFlrSF','2ndFlrSF','FullBath','BedroomAbvGr','TotRmsAbvGrd']
    X = data[columns_of_interest]
    y = data.SalePrice
    model = DecisionTreeRegressor()
    model.fit(X, y)
                        
    # print("Making predictions for the following 5 houses:")
    # print(X.head())
    # print("The predictions are")
    # print(model.predict(X.head()))

    # print(data.describe())
    # print(data.columns)

    print('in-sample mean absolute error')
    predicted_home_prices = model.predict(X)
    print(mean_absolute_error(y, predicted_home_prices))

    from sklearn.model_selection import train_test_split

    train_X, val_X, train_y, val_y = train_test_split(X,y,random_state = 0)
    model = DecisionTreeRegressor()
    model.fit(train_X, train_y)


![](http://ww1.sinaimg.cn/large/005N2p5vly1fu0wp0npeqj319u07wt9h.jpg)

### 4 优化模型到最小MAE


    import pandas as pd
    from sklearn.tree import DecisionTreeRegressor
    from sklearn.metrics import mean_absolute_error

    main_file_path = '../input/house-prices-advanced-regression-techniques/train.csv' # this is the path to the Iowa data that you will use
    data = pd.read_csv(main_file_path)

    columns_of_interest = ['LotArea','YearBuilt','1stFlrSF','2ndFlrSF','FullBath','BedroomAbvGr','TotRmsAbvGrd']
    X = data[columns_of_interest]
    y = data.SalePrice
    model = DecisionTreeRegressor()
    model.fit(X, y)

    print('in-sample mean absolute error')
    predicted_home_prices = model.predict(X)
    print(mean_absolute_error(y, predicted_home_prices))

    from sklearn.model_selection import train_test_split

    train_X, val_X, train_y, val_y = train_test_split(X,y,random_state = 0)
    model = DecisionTreeRegressor()
    model.fit(train_X, train_y)

    val_predictions = model.predict(val_X)
    print('\nadd train_test_split')
    print(mean_absolute_error(val_y, val_predictions))


    # a utility function to help compare MAE scores from different values for max_leaf_nodes:
    from sklearn.metrics import mean_absolute_error
    from sklearn.tree import DecisionTreeRegressor

    def get_mae(max_leaf_nodes, predictors_train, predictors_val, targ_train, targ_val):
        model = DecisionTreeRegressor(max_leaf_nodes=max_leaf_nodes, random_state=0)
        model.fit(predictors_train, targ_train)
        preds_val = model.predict(predictors_val)
        mae = mean_absolute_error(targ_val, preds_val)
        return(mae)

    # compare MAE with differing values of max_leaf_nodes
    for max_leaf_nodes in [5, 50, 500, 5000]:
        my_mae = get_mae(max_leaf_nodes, train_X, val_X, train_y, val_y)
        print("Max leaf nodes: %d \t\t Mean Absolute Error: %d" %(max_leaf_nodes, my_mae))


![](http://ww1.sinaimg.cn/large/005N2p5vly1fu0wnyv4a6j319q0cymzf.jpg)


### 5 随机森林

    import pandas as pd
    from sklearn.tree import DecisionTreeRegressor
    from sklearn.metrics import mean_absolute_error

    main_file_path = '../input/house-prices-advanced-regression-techniques/train.csv' # this is the path to the Iowa data that you will use
    data = pd.read_csv(main_file_path)

    columns_of_interest = ['LotArea','YearBuilt','1stFlrSF','2ndFlrSF','FullBath','BedroomAbvGr','TotRmsAbvGrd']
    X = data[columns_of_interest]
    y = data.SalePrice

    from sklearn.model_selection import train_test_split

    train_X, val_X, train_y, val_y = train_test_split(X,y,random_state = 0)

    from sklearn.ensemble import RandomForestRegressor
    from sklearn.metrics import mean_absolute_error

    forest_model = RandomForestRegressor(random_state=1)
    forest_model.fit(train_X, train_y)
    melb_preds = forest_model.predict(val_X)
    print(mean_absolute_error(val_y, melb_preds))


![](http://ww1.sinaimg.cn/large/005N2p5vly1fu0wrt5ceej31a602w74b.jpg)




































