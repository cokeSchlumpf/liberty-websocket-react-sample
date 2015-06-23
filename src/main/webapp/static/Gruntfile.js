module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    gruntMavenProperties: grunt.file.readJSON('grunt-maven.json'),

    copy: {
      dist: {
        files: [ 
          { src: [ '*.html' ], dest: 'dist/' },
          { expand: true, src: ['assets/**'], dest: 'dist/' }
        ]
      }
    },
    
    mavenPrepare: {
      options: {
        resources: [ '**' ]
      },
      prepare: {}
    },

    mavenDist: {
      options: {
        warName: '${project.build.finalName}',
        deliverables: [],
        gruntDistDir: 'dist'
      },
      dist: {}
    },

    run: {
      build: {
        options: {
          failOnError: true
        },
        cmd: 'npm',
        args: [ 'run', 'build' ]
      }      
    },

    watch: {
      maven: {
        files: [ '<%= gruntMavenProperties.filesToWatch %>' ],
        tasks: 'default'
      }
    }
  });

  grunt.loadNpmTasks('grunt-maven');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-run');

  grunt.registerTask('default', [ 'mavenPrepare', 'run:build', 'copy' ]);

};