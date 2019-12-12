import React from "react";
// react plugin for creating charts
import ChartistGraph from "react-chartist";
// @material-ui/core
import moment from 'moment';
import { makeStyles } from "@material-ui/core/styles";
import Icon from "@material-ui/core/Icon";
// @material-ui/icons
import Store from "@material-ui/icons/Store";
import Warning from "@material-ui/icons/Warning";
import DateRange from "@material-ui/icons/DateRange";
import LocalOffer from "@material-ui/icons/LocalOffer";
import Update from "@material-ui/icons/Update";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import AccessTime from "@material-ui/icons/AccessTime";
import Accessibility from "@material-ui/icons/Accessibility";
import BugReport from "@material-ui/icons/BugReport";
import Code from "@material-ui/icons/Code";
import Cloud from "@material-ui/icons/Cloud";
// core components
import GridItem from "components/Grid/GridItem.js";
import GridContainer from "components/Grid/GridContainer.js";
import Table from "components/Table/Table.js";
import Tasks from "components/Tasks/Tasks.js";
import CustomTabs from "components/CustomTabs/CustomTabs.js";
import Danger from "components/Typography/Danger.js";
import Card from "components/Card/Card.js";
import CardHeader from "components/Card/CardHeader.js";
import CardIcon from "components/Card/CardIcon.js";
import CardBody from "components/Card/CardBody.js";
import CardFooter from "components/Card/CardFooter.js";

import { bugs, website, server } from "variables/general.js";

import {
  dailySalesChart,
  emailsSubscriptionChart,
  completedTasksChart
} from "variables/charts.js";

import styles from "assets/jss/material-dashboard-react/views/dashboardStyle.js";

const useStyles = makeStyles(styles);


class Dashboard extends React.Component {

  constructor(props) {
    super(props);
    this.projectData = [];
    this.hackthons = [];
    this.top5Industry = [];
    this.top5Technology = [];
    this.topFiveProblems = [];

    this.state = {
      numTech: 0,
      numUser: 0,
      numProjects: 0,
    };
  }

  componentWillMount = () => { 
    this.getTop5Industry();
    this.getTop5Technology();
    this.getTopFiveProblems();
    this.getHackthons();
    this.getUniqueUser();
    this.getUniqueProjects();
    this.getUniqueTech();
   
   
  }

  getUniqueUser = () => { 
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getUniqueUserCount`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        this.setState({ numUser: responseJson });
        
        })
        .catch(error => console.error(error));

  }

  getHackthons = () => {
    fetch(process.env.REACT_APP_GIT_BASE+`/github_scanner/api/all`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        for (let i = 0; i < responseJson.length; i++) {
          let list = [];

          //push id
          list.push(i + 1);
          list.push(responseJson[i]);
            // console.log(key); // alerts key 
            //|| key=="Descriptions" || key=="Achievements" || key=="file_extension"
            
            // if(key=="hackthon_name"|| key=="project_name"|| key=="problem"|| key=="industry"|| key=="technology"||key=="email" ){
              this.hackthons.push(list);
       
        }
        console.log(typeof(this.hackthons));

        })
        .catch(error => console.error(error));

  //  = ["BUIDL Online", "Hachthon1"];

  }

  getUniqueTech = () => { 
    // 
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getUniqueTechCount`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        this.setState({ numTech: responseJson });
        
        })
        .catch(error => console.error(error));

  }

  getUniqueProjects = () => { 

    
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getTotalNumberOfProjects`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        this.setState({ numProjects: responseJson });
        
        })
        .catch(error => console.error(error));
  }

  getTop5Industry = () => { 
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getTop5Industry`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {

        Object.keys(responseJson)
          .forEach((key) => {
            let list = [];
            list.push(key);
            list.push(responseJson[key]);
            this.top5Industry.push(list);
          });
     
        // console.log(this.top5Industry);
        
        })
        .catch(error => console.error(error));

  }

  getTop5Technology = () => { 
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getTop5Technology`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        
        Object.keys(responseJson)
          .forEach((key) => {
            let list = [];
            list.push(key);
            list.push(responseJson[key]);
            this.top5Technology.push(list);
          });
        
        })
        .catch(error => console.error(error));

  }

  getTopFiveProblems = () => { 
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/topFiveProblems`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        // console.log(responseJson);
        Object.keys(responseJson)
        .forEach((key) => {
          let list = [];
          list.push(key);
          list.push(responseJson[key]);
          this.topFiveProblems.push(list);
        });
        })
        .catch(error => console.error(error));

  }






  render() {
    const { numTech,numUser,numProjects}=this.state;
  
  // const classes = useStyles();
  return (
    <div>
      <GridContainer>
        <GridItem xs={12} sm={6} md={4}>
          <Card>
            <CardHeader color="success" >
              <CardIcon color="warning">
                <Icon>content_copy</Icon>
              </CardIcon>
              <p col># Technologies</p>
              <h3 className={makeStyles.cardTitle}>
                {numTech}
                {/* <small>GB</small> */}
              </h3>
            </CardHeader>
            <CardFooter stats>
              <div className={makeStyles.stats}>
                <Update />
                Just Updated
              </div>
            </CardFooter>
          </Card>
        </GridItem>
        <GridItem xs={12} sm={6} md={4}>
          <Card>
            <CardHeader color="danger">
              <CardIcon color="danger">
                <Store />
              </CardIcon>
              <p className={makeStyles.cardCategory}># Projects</p>
              <h3 className={makeStyles.cardTitle}>{numProjects}</h3>
            </CardHeader>
            <CardFooter stats>
              <div className={makeStyles.stats}>
                <DateRange />
                Last 24 Hours
              </div>
            </CardFooter>
          </Card>
        </GridItem>
        {/* <GridItem xs={12} sm={6} md={3}>
          <Card>
            <CardHeader color="danger" stats icon>
              <CardIcon color="danger">
                <Icon>info_outline</Icon>
              </CardIcon>
              <p className={classes.cardCategory}>Fixed Issues</p>
              <h3 className={classes.cardTitle}>75</h3>
            </CardHeader>
            <CardFooter stats>
              <div className={classes.stats}>
                <LocalOffer />
                Tracked from Github
              </div>
            </CardFooter>
          </Card>
        </GridItem> */}
        <GridItem xs={12} sm={6} md={4}>
          <Card>
            <CardHeader color="info" >
              <CardIcon color="warning">
                <Accessibility />
              </CardIcon>
              <p className={makeStyles.cardCategory}># Users</p>
              <h3 className={makeStyles.cardTitle}>{numUser}</h3>
            </CardHeader>
            <CardFooter stats>
              <div className={makeStyles.stats}>
                <Update />
                Just Updated
              </div>
            </CardFooter>
          </Card>
        </GridItem>
      </GridContainer>
      {/* <GridContainer>
        <GridItem xs={12} sm={12} md={4}>
          <Card chart>
            <CardHeader color="success">
              <ChartistGraph
                className="ct-chart"
                data={dailySalesChart.data}
                type="Line"
                options={dailySalesChart.options}
                listener={dailySalesChart.animation}
              />
            </CardHeader>
            <CardBody>
              <h4 className={classes.cardTitle}>Daily Sales</h4>
              <p className={classes.cardCategory}>
                <span className={classes.successText}>
                  <ArrowUpward className={classes.upArrowCardCategory} /> 55%
                </span>{" "}
                increase in today sales.
              </p>
            </CardBody>
            <CardFooter chart>
              <div className={classes.stats}>
                <AccessTime /> updated 4 minutes ago
              </div>
            </CardFooter>
          </Card>
        </GridItem>
        <GridItem xs={12} sm={12} md={4}>
          <Card chart>
            <CardHeader color="warning">
              <ChartistGraph
                className="ct-chart"
                data={emailsSubscriptionChart.data}
                type="Bar"
                options={emailsSubscriptionChart.options}
                responsiveOptions={emailsSubscriptionChart.responsiveOptions}
                listener={emailsSubscriptionChart.animation}
              />
            </CardHeader>
            <CardBody>
              <h4 className={classes.cardTitle}>Email Subscriptions</h4>
              <p className={classes.cardCategory}>Last Campaign Performance</p>
            </CardBody>
            <CardFooter chart>
              <div className={classes.stats}>
                <AccessTime /> campaign sent 2 days ago
              </div>
            </CardFooter>
          </Card>
        </GridItem>
        <GridItem xs={12} sm={12} md={4}>
          <Card chart>
            <CardHeader color="danger">
              <ChartistGraph
                className="ct-chart"
                data={completedTasksChart.data}
                type="Line"
                options={completedTasksChart.options}
                listener={completedTasksChart.animation}
              />
            </CardHeader>
            <CardBody>
              <h4 className={classes.cardTitle}>Completed Tasks</h4>
              <p className={classes.cardCategory}>Last Campaign Performance</p>
            </CardBody>
            <CardFooter chart>
              <div className={classes.stats}>
                <AccessTime /> campaign sent 2 days ago
              </div>
            </CardFooter>
          </Card>
        </GridItem>
      </GridContainer> */}
      <GridContainer>
      <GridItem xs={12} sm={12} md={6}>
          <Card>
            <CardHeader color="primary">
              <h4 className={makeStyles.cardTitleWhite}>Hackthons</h4>
              
              <p className={makeStyles.cardCategoryWhite}>
                Hackthons up till {moment(new Date()).format('YYYY-MM-DD')}
              </p>
            </CardHeader>
            <CardBody>
              <Table
                tableHeaderColor="warning"
                tableHead={["ID", "Name"]}
                tableData={this.hackthons}
              />
            </CardBody>
          </Card>
        </GridItem>
      {/* primary */}
        {/* <GridItem xs={12} sm={12} md={6}>
          <CustomTabs
            title="Tasks:"
            headerColor="primary"
            tabs={[
              {
                tabName: "Bugs",
                tabIcon: BugReport,
                tabContent: (
                  <Tasks
                    checkedIndexes={[0, 3]}
                    tasksIndexes={[0, 1, 2, 3]}
                    tasks={bugs}
                  />
                )
              },
              {
                tabName: "Website",
                tabIcon: Code,
                tabContent: (
                  <Tasks
                    checkedIndexes={[0]}
                    tasksIndexes={[0, 1]}
                    tasks={website}
                  />
                )
              },
              {
                tabName: "Server",
                tabIcon: Cloud,
                tabContent: (
                  <Tasks
                    checkedIndexes={[1]}
                    tasksIndexes={[0, 1, 2]}
                    tasks={server}
                  />
                )
              }
            ]}
          />
        </GridItem> */}
        <GridItem xs={12} sm={12} md={6}>
          <Card>
            <CardHeader color="warning">
              <h4 className={makeStyles.cardTitleWhite}>Top 5 Industries</h4>
              {/* <p className={makeStyles.cardCategoryWhite}>
                New employees on 15th September, 2016
              </p> */}
            </CardHeader>
            <CardBody>
              <Table
                tableHeaderColor="warning"
                tableHead={["Name", "Quantity"]}
                tableData={this.top5Industry}
              />
            </CardBody>
          </Card>
        </GridItem>
        <GridItem xs={12} sm={12} md={6}>
          <Card>
            <CardHeader color="warning">
              <h4 className={makeStyles.cardTitleWhite}>Top 5 Technologies</h4>
              {/* <p className={makeStyles.cardCategoryWhite}>
                New employees on 15th September, 2016
              </p> */}
            </CardHeader>
            <CardBody>
              <Table
                tableHeaderColor="warning"
                tableHead={["Name", "Quantity"]}
                tableData={this.top5Technology}
              />
            </CardBody>
          </Card>
        </GridItem>
        <GridItem xs={12} sm={12} md={6}>
          <Card>
            <CardHeader color="warning">
              <h4 className={makeStyles.cardTitleWhite}>Top 5 Problems</h4>
              {/* <p className={makeStyles.cardCategoryWhite}>
                New employees on 15th September, 2016
              </p> */}
            </CardHeader>
            <CardBody>
              <Table
                tableHeaderColor="warning"
                tableHead={["Name", "Quantity"]}
                tableData={this.topFiveProblems}
              />
            </CardBody>
          </Card>
        </GridItem>
      </GridContainer>
    </div>
  );
}

}

export default Dashboard;