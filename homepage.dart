import 'package:flutter/material.dart';
import 'package:sport_for_development/widgets/constant.dart';

import '../models/class.dart';
import '../models/student.dart';
import '../models/user.dart';
import '../services/classservice.dart';
import '../services/studentservice.dart';
import '../widgets/buildClassesWidget.dart';
import 'package:sport_for_development/widgets/buildStudentWidget.dart';

import '../widgets/mydrawer.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // TODO change query to user's respective id
  late final ScrollController _scrollController;

  late Future<List<Class>> futureClass = Future.value([]);
  late Future<List<Student>> futureStudent = Future.value([]);

  @override
  void initState() {
    super.initState();

    _scrollController = ScrollController();

    fetchClassAndStudent();

    // setState(() {
    //   User.classes = futureClass;
    //   User.students = futureStudent;
    // });
  }


  Future<void> fetchClassAndStudent() async {
    final classData = await fetchClass();
    final studentData = await fetchStudent();

    futureClass = Future.value(classData);
    futureStudent = Future.value(studentData);

    setState(() {
      User.classes = classData;
      User.students = studentData;
    });
  }


  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[300],
      appBar: appBar(null),
      drawer: MyDrawer(),
      body: Scrollbar(
        controller: _scrollController,
        thumbVisibility: true,
        child: SingleChildScrollView (
          controller: _scrollController,
          child: Padding (
            padding: const EdgeInsets.symmetric(horizontal: 30, vertical: 5),
            child: Column (
              children: [
                SizedBox(
                  width: double.infinity,
                  height: 150,
                  child: ElevatedButton(
                    // style: ElevatedButton.styleFrom(
                    //     textStyle: const TextStyle(fontSize: 26)
                    // ),
                    style: ButtonStyle(
                      shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(18.0),
                        )
                      ),
                    ),
                    onPressed: () {
                      Navigator.pushNamed(context, '/pickClass');
                    },
                    child: const Text(
                      'Start Assessments',
                      style: TextStyle(
                        fontSize: 26
                      ),
                    )
                  )
                ),
                Align(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    "Recent Courses",
                  ),
                ),
                Container(
                  height: 270,
                  child: FutureBuilder<List<Class>>(
                    future: futureClass,
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {
                        final classData = snapshot.data!;

                        // TODO add isLoading boolean and if it💪
                        if(classData.isEmpty) {
                          return Center(child: Text("No Class"),);
                        } else {
                          return ListView.builder(
                              scrollDirection: Axis.horizontal,
                              itemCount: classData.length,
                              itemBuilder: (context, index) {
                                return BuildClassesWidget(classData: classData[index]);
                              }
                          );
                        }
                      } else if (snapshot.hasError) {
                        return Text('${snapshot.error}');
                      }
                      return const CircularProgressIndicator(); // show spinner
                    },
                  ),
                ),
                Align(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    "Recent Students",
                  ),
                ),
                SizedBox (
                  height: 400,
                  child: FutureBuilder<List<Student>>(
                    future: futureStudent,
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {
                        final studentData = snapshot.data!;

                        if(studentData.isEmpty) {
                          return Center(child: Text("No Student"),);
                        } else {
                          return BuildStudentWidget();
                        }
                      } else if (snapshot.hasError) {
                        return Text('${snapshot.error}');
                      }
                      return const CircularProgressIndicator(); // show spinner
                    },
                  ),
                )
              ],
            ),
          )
        )
      ),
    );
  }
}