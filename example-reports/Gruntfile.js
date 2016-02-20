'use strict';

module.exports = function(grunt) {
 grunt.initConfig({
  jshint: {
   all: [
    'web/*.js'
   ],
   options: {
    "undef": true,
    "unused": true,
    reporter: 'jslint',
    reporterOutput: '../src/test/resources/jshint/jshint.xml',
    force: true,
   },
  },

  csslint: {
   all: [
    'web/*.css'
   ],

   options: {
    formatters: [
      {id: 'csslint-xml', dest: '../src/test/resources/csslint/csslint.xml'}
    ],
    force: true,
   },
  }
 });

 grunt.loadNpmTasks('grunt-contrib-jshint');
 grunt.loadNpmTasks('grunt-contrib-csslint');
 grunt.registerTask('default', ['jshint', 'csslint']);
};
